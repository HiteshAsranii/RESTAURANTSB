package com.restaurant.apis.service;

import java.time.LocalDate;
import java.util.List;

import com.restaurant.apis.model.DailySales;
import com.restaurant.apis.model.HighestSellingMenuItemByCategory;
import com.restaurant.apis.model.MonthlySales;
import com.restaurant.apis.model.OrderDTO;
import com.restaurant.apis.model.OrderItems;
import com.restaurant.apis.model.OrderRequestWrapper;
import com.restaurant.apis.model.Orders;
import com.restaurant.apis.model.YearlySales;

import jakarta.transaction.Transactional;

@Transactional
public interface OrderService {
    public Orders createOrder(Orders order, List<OrderItems> orderItems);
    public OrderRequestWrapper getOrderDetails(int tableId);
    public Orders updateOrder(Orders order, List<OrderItems> orderItems);
    public List<OrderRequestWrapper> getOrderByDuration(LocalDate date);
    public Orders changeOrderStatusToPreparing(int orderId);
    public Orders changeOrderStatusToComplete(Orders order);
    public List<OrderDTO> getOrdersForKitchenScheduling();
    public List<HighestSellingMenuItemByCategory> getHighestSellingMenuItemByCategory();
    public List<YearlySales> getYearlySales();
    public List<MonthlySales> getMonthlySales();
    public List<DailySales> getDailySales();


}
