package com.restaurant.apis.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.apis.Model.DailySales;
import com.restaurant.apis.Model.HighestSellingMenuItemByCategory;
import com.restaurant.apis.Model.MonthlySales;
import com.restaurant.apis.Model.OrderDTO;
import com.restaurant.apis.Model.OrderRequestWrapper;
import com.restaurant.apis.Model.Orders;
import com.restaurant.apis.Model.YearlySales;
import com.restaurant.apis.Service.OrderService;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("orders")
@CrossOrigin(origins = "http://localhost:5173/")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        super();
        this.orderService = orderService;
    }

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @PostMapping("createOrder")
    public Orders createOrder(@RequestBody OrderRequestWrapper orderRequest) {
        logger.info(orderRequest.toString());
        return orderService.createOrder(orderRequest.getOrder(), orderRequest.getOrderItems());
    }

    @GetMapping("getOrderDetails")
    public OrderRequestWrapper getorderDetails(@RequestParam int tableId) {
        return orderService.getOrderDetails(tableId);
    }

    @PostMapping("updateOrder")
    public Orders updateOrder(@RequestBody OrderRequestWrapper orderRequest) {
        return orderService.updateOrder(orderRequest.getOrder(), orderRequest.getOrderItems());
    }

    @GetMapping("getOrderAfterDate")
    public List<OrderRequestWrapper> ordersAfterDate(@RequestParam LocalDate date) {
        return orderService.getOrderByDuration(date);
    }

    @GetMapping("getItemsForKitchenScheduling")
    public List<OrderDTO> getItemsForKitchenScheduling() {
        return orderService.getOrdersForKitchenScheduling();
    }

    @GetMapping("getHighestSellingMenuItems")
    public List<HighestSellingMenuItemByCategory> getHighestSellingMenuItemByCategory() {
        return orderService.getHighestSellingMenuItemByCategory();
    }

    @GetMapping("getYearlySales")
    public List<YearlySales> getYearlySales(){
        return orderService.getYearlySales();
    }

    @GetMapping("getMonthlySales")
    public List<MonthlySales> getMonthlySales(){
        return orderService.getMonthlySales();
    }

    @GetMapping("getDailySales")
    public List<DailySales> getDailySales(){
        return orderService.getDailySales();
    }

    @PostMapping("startPreparingOrder")
    public Orders startPreparingOrder(@RequestParam int orderId){
        return orderService.changeOrderStatusToPreparing(orderId);
    }

}
