package com.restaurant.apis.Service;

import java.time.LocalDate;
import java.util.List;

import com.restaurant.apis.Model.DailySales;
import com.restaurant.apis.Model.HighestSellingMenuItemByCategory;
import com.restaurant.apis.Model.MonthlySales;
import com.restaurant.apis.Model.OrderDTO;
import com.restaurant.apis.Model.OrderItems;
import com.restaurant.apis.Model.OrderRequestWrapper;
import com.restaurant.apis.Model.Orders;
import com.restaurant.apis.Model.YearlySales;

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
