package com.restaurant.apis.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.apis.Model.OrderRequestWrapper;
import com.restaurant.apis.Model.Orders;
import com.restaurant.apis.Service.OrderService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("orders")
@CrossOrigin(origins = "http://localhost:5173/")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService){
        super();
        this.orderService = orderService;
    }
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @PostMapping("createOrder")
    public Orders createOrder(@RequestBody OrderRequestWrapper orderRequest){
        logger.info(orderRequest.toString());
        return orderService.createOrder(orderRequest.getOrder(), orderRequest.getOrderItems());
    }

    @GetMapping("getOrderDetails")
    public OrderRequestWrapper getorderDetails(@RequestParam int tableId){
        return orderService.getOrderDetails(tableId);
    }
    @PostMapping("updateOrder")
    public Orders updateOrder(@RequestBody OrderRequestWrapper orderRequest){
        return orderService.updateOrder(orderRequest.getOrder(), orderRequest.getOrderItems());
    }

}
