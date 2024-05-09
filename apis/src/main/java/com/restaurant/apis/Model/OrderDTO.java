package com.restaurant.apis.Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.Data;

@Data
public class OrderDTO {
    private int orderId;
    private LocalDate orderDate;
    private LocalTime orderTime;
    private List<OrderItemDTO> orderItems;
}


