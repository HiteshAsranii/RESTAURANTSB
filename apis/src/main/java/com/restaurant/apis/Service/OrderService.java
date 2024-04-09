package com.restaurant.apis.Service;

import java.time.LocalDate;
import java.util.List;

import com.restaurant.apis.Model.OrderItems;
import com.restaurant.apis.Model.OrderRequestWrapper;
import com.restaurant.apis.Model.Orders;

import jakarta.transaction.Transactional;

@Transactional
public interface OrderService {
    public Orders createOrder(Orders order, List<OrderItems> orderItems);
    public OrderRequestWrapper getOrderDetails(int tableId);
    public Orders updateOrder(Orders order, List<OrderItems> orderItems);
    public List<OrderRequestWrapper> getOrderByDuration(LocalDate date);

}
