package com.restaurant.apis.Service.Implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

}
