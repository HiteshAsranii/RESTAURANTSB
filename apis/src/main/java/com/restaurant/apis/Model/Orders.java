package com.restaurant.apis.Model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "OrderId")
    private int OrderId;

    @ManyToOne
    @JoinColumn(name = "TableId", nullable = false)
    private RestaurantTable TableId;

    @Column(name = "CustomerName", nullable = false)
    private String CustomerName;

    @Column(name = "phoneNo", nullable = false)
    private Long phoneNo;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "OrderDate", nullable = false)
    private LocalDate OrderDate;

    @Column(name = "OrderTime", nullable = false)
    private LocalTime OrderTime;

    @Column(name = "OrderStatus", nullable = false)
    private String orderStatus;

    @Column(name = "OrderSubTotal", nullable = false)
    private double OrderSubTotal;

    @Column(name = "Tax", nullable = false)
    private double tax;

    @Column(name = "OrderTotal", nullable = false)
    private double OrderTotal;

}
