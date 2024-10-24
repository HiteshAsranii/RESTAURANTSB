package com.restaurant.apis.model;

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
@Table(name = "menu_items")
@Data
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MenuItemId", nullable = false)
    private int MenuItemId;

    @ManyToOne
    @JoinColumn(name = "MenuCategoryId" , nullable = false)
    private MenuCategory MenuCategoryId;

    @Column(name = "MenuItemName" , nullable = false)
    private String MenuItemName;

    @Column(name = "MenuItemPrice" , nullable = false)
    private double MenuItemPrice;

    @Column(name = "MenuItemDescription", nullable = false)
    private String MenuItemDescription;

    @Column(name = "Available" , nullable = false)
    private boolean Available;
}
