package com.restaurant.apis.Service;

import java.util.List;

import com.restaurant.apis.Model.OrderItems;
import com.restaurant.apis.Model.Orders;

import jakarta.transaction.Transactional;

@Transactional
public interface OrderService {
    public Orders createOrder(Orders order, List<OrderItems> orderItems);

}
