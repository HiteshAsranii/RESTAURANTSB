package com.restaurant.apis.Service.Implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.restaurant.apis.Model.OrderItems;
import com.restaurant.apis.Model.OrderRequestWrapper;
import com.restaurant.apis.Model.Orders;
import com.restaurant.apis.Model.RestaurantTable;
import com.restaurant.apis.Service.OrderService;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

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
        Orders order = entityManager
                .createQuery("SELECT o FROM Orders o WHERE o.TableId.TableId = :tableId",
                        Orders.class)
                .setParameter("tableId", tableId)
                .getSingleResult();

        List<OrderItems> orderItemsList = entityManager
                .createQuery("SELECT oi FROM OrderItems oi WHERE oi.OrderId.OrderId = :orderId", OrderItems.class)
                .setParameter("orderId", order.getOrderId())
                .getResultList();

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
        for(OrderItems item: ol) entityManager.remove(item);
        for(OrderItems item: orderItems){
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

}
