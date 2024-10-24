package com.restaurant.apis.model;

import java.util.List;
import lombok.Data;

@Data
public class OrderRequestWrapper {

    private Orders order;
    private List<OrderItems> orderItems;
}
