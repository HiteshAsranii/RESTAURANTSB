package com.restaurant.apis.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "menu_category")
@Entity
public class MenuCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MenuCategoryId", nullable = false)
    private int MenuCategoryId;

    @Column(name = "MenuCategoryName", nullable = false)
    private String MenuCategoryName;

    public int getMenuCategoryId() {
        return MenuCategoryId;
    }

    public void setMenuCategoryId(int menuCategoryId) {
        MenuCategoryId = menuCategoryId;
    }

    public String getMenuCategoryName() {
        return MenuCategoryName;
    }

    public void setMenuCategoryName(String menuCategoryName) {
        MenuCategoryName = menuCategoryName;
    }

}
