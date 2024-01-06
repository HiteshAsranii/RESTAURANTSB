package com.restaurant.apis.Model;

import java.util.List;
import lombok.Data;

@Data
public class OrderRequestWrapper {

    private Orders order;
    private List<OrderItems> orderItems;
}
