package com.restaurant.apis.Service.Implementation;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.apis.Model.DailySales;
import com.restaurant.apis.Model.HighestSellingMenuItemByCategory;
import com.restaurant.apis.Model.MonthlySales;
import com.restaurant.apis.Model.OrderDTO;
import com.restaurant.apis.Model.OrderItemDTO;
import com.restaurant.apis.Model.OrderItems;
import com.restaurant.apis.Model.OrderRequestWrapper;
import com.restaurant.apis.Model.Orders;
import com.restaurant.apis.Model.RestaurantTable;
import com.restaurant.apis.Model.YearlySales;
import com.restaurant.apis.Service.OrderService;
import com.restaurant.apis.WebSocket.WebSocketPublisher;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;


@Transactional
@Service
public class OrderServiceImplementation implements OrderService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private WebSocketPublisher webSocketPublisher;
    final Logger logger = LoggerFactory.getLogger(PaymentServiceImplementation.class);

    @Override
    public Orders createOrder(Orders order, List<OrderItems> orderItems) {
        RestaurantTable t = entityManager.find(RestaurantTable.class, order.getTableId().getTableId());
        // update this use restaurant table service
        t.setTableStatus(true);
        entityManager.merge(t);
        entityManager.persist(order);

        for (OrderItems o : orderItems) {
            o.setOrderId(order);
            entityManager.persist(o);
        }
        webSocketPublisher.sendOrderPlacedMessage();
        return order;
    }

    @Override
    public OrderRequestWrapper getOrderDetails(int tableId) {
        Orders order = null;
        List<OrderItems> orderItemsList = null;
        try {
            order = entityManager
                    .createQuery(
                            "SELECT o FROM Orders o WHERE o.TableId.TableId = :tableId ORDER by o.OrderId desc limit 1",
                            Orders.class)
                    .setParameter("tableId", tableId)
                    .getSingleResult();

            orderItemsList = entityManager
                    .createQuery("SELECT oi FROM OrderItems oi WHERE oi.OrderId.OrderId = :orderId", OrderItems.class)
                    .setParameter("orderId", order.getOrderId())
                    .getResultList();
        } catch (NoResultException e) {

        }

        if (order == null || orderItemsList == null) {
            return null;
        }

        OrderRequestWrapper orderDetails = new OrderRequestWrapper();
        orderDetails.setOrder(order);
        orderDetails.setOrderItems(orderItemsList);
        return orderDetails;
    }

    @Override
    public Orders updateOrder(Orders order, List<OrderItems> orderItems) {
        List<OrderItems> ol = entityManager
                .createQuery("Select oi from OrderItems oi where oi.OrderId.OrderId = :orderId", OrderItems.class)
                .setParameter("orderId", order.getOrderId())
                .getResultList();
        for (OrderItems item : ol)
            entityManager.remove(item);
        for (OrderItems item : orderItems) {
            item.setOrderId(order);
            entityManager.persist(item);
        }
        Orders oldOrder = entityManager.find(Orders.class, order.getOrderId());
        oldOrder.setOrderSubTotal(order.getOrderSubTotal());
        oldOrder.setTax(order.getTax());
        oldOrder.setOrderTotal(order.getOrderTotal());
        entityManager.merge(oldOrder);
        webSocketPublisher.sendOrderUpdatedMessage();
        return order;
    }

    @Override
    public List<OrderRequestWrapper> getOrderByDuration(LocalDate date) {
        try {
            List<Orders> orders = entityManager
                    .createQuery("select o from Orders o where o.OrderDate >= :date", Orders.class)
                    .setParameter("date", date).getResultList();
            List<OrderRequestWrapper> ordersAfterDate = new ArrayList<>();
            for (Orders order : orders) {
                List<OrderItems> orderItems = entityManager
                        .createQuery("select oi from OrderItems oi where oi.OrderId.OrderId =:order", OrderItems.class)
                        .setParameter("order", order.getOrderId()).getResultList();
                OrderRequestWrapper orderRequestWrapper = new OrderRequestWrapper();
                orderRequestWrapper.setOrder(order);
                orderRequestWrapper.setOrderItems(orderItems);
                ordersAfterDate.add(orderRequestWrapper);
            }
            return ordersAfterDate;

        } catch (NoResultException e) {
            return Collections.emptyList();
        }

    }

    @Override
    public Orders changeOrderStatusToPreparing(Orders order) {
        order.setOrderStatus("Preparing");
        webSocketPublisher.sendKitchenStatusUpdated();
        return order;
    }

    @Override
    public Orders changeOrderStatusToComplete(Orders order) {
        order.setOrderStatus("Completed");
        return order;

    }

    public List<OrderDTO> getOrdersForKitchenScheduling() {
        List<OrderDTO> orderDTOList = new ArrayList<>();

        try {
            List<Orders> activeOrders = entityManager
                    .createQuery(
                            "SELECT o FROM Orders o WHERE o.orderStatus = :orderStatus order by o.OrderDate , o.OrderTime",
                            Orders.class)
                    .setParameter("orderStatus", "Active")
                    .getResultList();

            for (Orders order : activeOrders) {
                OrderDTO orderDTO = new OrderDTO();
                orderDTO.setOrderId(order.getOrderId());
                orderDTO.setOrderDate(order.getOrderDate());
                orderDTO.setOrderTime(order.getOrderTime());

                // Fetch order items associated with the current order
                List<OrderItemDTO> orderItems = new ArrayList<>();
                List<OrderItems> orderItemsEntities = entityManager
                        .createQuery("SELECT oi FROM OrderItems oi WHERE oi.OrderId.OrderId = :orderId",
                                OrderItems.class)
                        .setParameter("orderId", order.getOrderId())
                        .getResultList();

                for (OrderItems orderItem : orderItemsEntities) {
                    OrderItemDTO orderItemDTO = new OrderItemDTO();
                    orderItemDTO.setMenuItemName(orderItem.getMenuItemId().getMenuItemName());
                    orderItems.add(orderItemDTO);
                }
                orderDTO.setOrderItems(orderItems);

                orderDTOList.add(orderDTO);
            }

            return orderDTOList;
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

    public List<HighestSellingMenuItemByCategory> getHighestSellingMenuItemByCategory() {
        String sql = "WITH RankedMenuItems AS ( " +
                "    SELECT " +
                "        mi.menu_item_id, " +
                "        mi.menu_category_id, " +
                "        mi.menu_item_name," +
                "        COUNT(*) AS sale_count, " +
                "        ROW_NUMBER() OVER (PARTITION BY mi.menu_category_id ORDER BY COUNT(*) DESC) AS rank " +
                "    FROM " +
                "        order_items oi " +
                "        INNER JOIN menu_items mi ON oi.menu_item_id = mi.menu_item_id " +
                "    GROUP BY " +
                "        mi.menu_item_id, mi.menu_category_id , mi.menu_item_name" +
                ") " +
                "SELECT " +
                "    COALESCE(rmi.menu_item_id, 0) AS menu_item_id, " +
                "    COALESCE(rmi.sale_count, 0) AS sale_count, " +
                "    COALESCE(rmi.menu_item_name, 'None') AS menu_item_name, " +
                "    COALESCE(rmi.menu_category_id, mc.menu_category_id) AS menu_category_id, " +
                "    mc.menu_category_name " +
                "FROM " +
                "    menu_category mc " +
                "    LEFT JOIN RankedMenuItems rmi ON mc.menu_category_id = rmi.menu_category_id " +
                "    LEFT JOIN menu_items mi ON rmi.menu_item_id = mi.menu_item_id " +
                "WHERE " +
                "    rmi.rank = 1 OR rmi.rank IS NULL";

        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> results = query.getResultList();
        List<HighestSellingMenuItemByCategory> resultList = new ArrayList<>();
        for (Object[] row : results) {
            Integer menuItemId = (Integer) row[0]; // Assuming the first column is menu_item_id
            Integer saleCount = (Integer) row[1]; // Assuming the second column is sale_count
            String menuItemName = (String) row[4]; // Assuming the third column is menu_item_name
            Integer menuCategoryId = (Integer) row[3]; // Assuming the fourth column is menu_category_id
            String menuCategoryName = (String) row[2]; // Assuming the fifth column is menu_category_name
            HighestSellingMenuItemByCategory item = new HighestSellingMenuItemByCategory(menuItemId, saleCount,
                    menuCategoryId, menuItemName, menuCategoryName);
            resultList.add(item);

        }
        return resultList;
    }

    @Override
    public List<YearlySales> getYearlySales() {
        String sql = "WITH Years AS ( " +
                "    SELECT YEAR(GETDATE()) AS year " +
                "    UNION ALL " +
                "    SELECT year - 1 " +
                "    FROM Years " +
                "    WHERE year > YEAR(GETDATE()) - 3 " +
                "), " +
                "Sales AS ( " +
                "    SELECT YEAR(order_date) AS year, SUM(order_total) AS total " +
                "    FROM orders " +
                "    GROUP BY YEAR(order_date) " +
                ") " +
                "SELECT y.year, COALESCE(s.total, 0) AS total " +
                "FROM Years y " +
                "LEFT JOIN Sales s ON y.year = s.year " +
                "ORDER BY y.year;";

        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> resultList = query.getResultList();

        List<YearlySales> yearlySalesList = new ArrayList<>();
        for (Object[] result : resultList) {
            YearlySales yearlySales = new YearlySales();
            yearlySales.setYear((Integer) result[0]);
            yearlySales.setAmount((Double) result[1]);
            yearlySalesList.add(yearlySales);
        }

        return yearlySalesList;
    }

    @Override
    public List<MonthlySales> getMonthlySales() {
        String sql = "WITH Months AS (" +
                "    SELECT 1 AS month " +
                "    UNION ALL " +
                "    SELECT month + 1 " +
                "    FROM Months " +
                "    WHERE month < 12 " +
                "), " +
                "Sales AS (" +
                "    SELECT MONTH(order_date) AS month, SUM(order_total) AS total " +
                "    FROM orders " +
                "    GROUP BY MONTH(order_date) " +
                ") " +
                "SELECT m.month, COALESCE(s.total, 0) AS total " +
                "FROM Months m " +
                "LEFT JOIN Sales s ON m.month = s.month " +
                "ORDER BY m.month;";

        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> resultList = query.getResultList();

        List<MonthlySales> monthlySalesList = new ArrayList<>();
        for (Object[] result : resultList) {
            MonthlySales monthlySales = new MonthlySales();
            monthlySales.setMonth((Integer) result[0]);
            monthlySales.setAmount((Double) result[1]);
            monthlySalesList.add(monthlySales);
        }

        return monthlySalesList;
    }

    @Override
    public List<DailySales> getDailySales() {
        String sql = "WITH DateRange AS (" +
                "    SELECT TOP 7 CAST(DATEADD(DAY, -ROW_NUMBER() OVER (ORDER BY (SELECT NULL)), GETDATE()) AS DATE) AS date "
                +
                "    FROM sys.objects " +
                ") " +
                "SELECT " +
                "    d.date, " +
                "    COALESCE(SUM(o.order_total), 0) AS total " +
                "FROM " +
                "    DateRange d " +
                "LEFT JOIN " +
                "    orders o ON CAST(o.order_date AS DATE) = d.date " +
                "GROUP BY " +
                "    d.date " +
                "ORDER BY " +
                "    d.date DESC;";

        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> resultList = query.getResultList();

        List<DailySales> dailySalesList = new ArrayList<>();
        for (Object[] result : resultList) {
            DailySales dailySales = new DailySales();
            dailySales.setDate(((java.sql.Date) result[0]).toLocalDate()); // Assuming LocalDate is used
            dailySales.setAmount((Double) result[1]);
            dailySalesList.add(dailySales);
        }

        return dailySalesList;
    }
}
