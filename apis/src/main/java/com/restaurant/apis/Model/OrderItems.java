package com.restaurant.apis.Model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "OrderItems")
public class OrderItems {
    @Id
    @ManyToOne
    @JoinColumn(name = "OrderId")
    private Orders OrderId;

    @Id
    @ManyToOne
    @JoinColumn(name = "MenuItemId")
    private MenuItem MenuItemId;

    @Column(name = "Quantity", nullable = false)
    private int Quantity;

    @Column(name = "Subtotal", nullable = false)
    private double Subtotal;

    public OrderItems(Orders OrderId, MenuItem MenuItemId){
        this.OrderId=OrderId;
        this.MenuItemId = MenuItemId;
    }

    public OrderItems(){
        
    }
}

