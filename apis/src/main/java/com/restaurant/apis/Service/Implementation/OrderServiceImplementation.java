package com.restaurant.apis.Service.Implementation;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.apis.Model.OrderItems;
import com.restaurant.apis.Model.OrderRequestWrapper;
import com.restaurant.apis.Model.Orders;
import com.restaurant.apis.Model.RestaurantTable;
import com.restaurant.apis.Service.OrderService;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import jakarta.persistence.NoResultException;
import java.time.LocalDate;

@Transactional
@Service
public class OrderServiceImplementation implements OrderService {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Orders createOrder(Orders order, List<OrderItems> orderItems) {
        RestaurantTable t = entityManager.find(RestaurantTable.class, order.getTableId().getTableId());
        t.setTableStatus(true);
        entityManager.merge(t);
        entityManager.persist(order);

        for (OrderItems o : orderItems) {
            o.setOrderId(order);
            entityManager.persist(o);
        }
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
            // Handle the case where no result is found
            // For example, you can log the error or return null
            System.out.println("No order found for table ID: " + tableId);
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
        // for(OrderItems o: orderItems){
        // OrderItems orderId = new OrderItems(order, o.getMenuItemId());
        // OrderItems existingItem = entityManager.find(OrderItems.class, orderId);

        // if (existingItem != null) {
        // // Update existing order item's quantity
        // existingItem.setQuantity(o.getQuantity());
        // entityManager.merge(existingItem);
        // } else {
        // // Persist new order item
        // o.setOrderId(order);
        // entityManager.persist(o);
        // }
        // }
        // Orders oldOrder = entityManager.find(Orders.class, order.getOrderId());
        // oldOrder.setOrderTotal(order.getOrderTotal());
        // oldOrder.setOrderSubTotal(order.getOrderSubTotal());
        // oldOrder.setTax(order.getTax());
        // entityManager.merge(oldOrder);
        // return order;
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

}
