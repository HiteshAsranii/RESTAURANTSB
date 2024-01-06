package com.restaurant.apis.Service.Implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.apis.Model.OrderItems;
import com.restaurant.apis.Model.Orders;
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
        entityManager.persist(order);

        for (OrderItems o : orderItems) {
            o.setOrderId(order);
            entityManager.persist(o);
        }
        return order;
    }

}
