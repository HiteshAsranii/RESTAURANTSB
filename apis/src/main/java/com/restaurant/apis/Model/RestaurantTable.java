package com.restaurant.apis.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "RestaurantTable")
@Data
public class RestaurantTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TableId")
    private int TableId;

    @Column(name = "TableCapacity", nullable = false)
    private int TableCapacity;

    @Column(name = "TableStatus", nullable = false)
    private boolean TableStatus;

}
