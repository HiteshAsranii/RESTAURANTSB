package com.restaurant.apis.Model;
import java.time.LocalDate;

import lombok.Data;

@Data
public class DailySales {
    private LocalDate date;
    private double amount;
}
