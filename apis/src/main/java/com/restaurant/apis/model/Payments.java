package com.restaurant.apis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Payments")
@Data
public class Payments {

    @Id
    @Column(name = "paymentId")
    private String paymentId;

    @Column(name = "razorPayId")
    private String razorPayId;

    @Column(name = "paymentMethod")
    private String paymentMethod;

    @Column(name = "amount")
    private double amount;
    
    @OneToOne
    @JoinColumn(name = "OrderId")
    private Orders orderId;
    
}
