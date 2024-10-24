package com.restaurant.apis.model;

import lombok.Data;

@Data
public class OrderItemDTO {
    private String menuItemName;
    private int quantity;
}