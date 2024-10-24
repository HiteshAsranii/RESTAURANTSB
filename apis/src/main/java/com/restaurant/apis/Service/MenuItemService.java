package com.restaurant.apis.service;

import java.util.List;

import com.restaurant.apis.model.MenuItem;

import jakarta.transaction.Transactional;

@Transactional
public interface MenuItemService {
    public List<MenuItem> addMenuItems(List<MenuItem> menuItemList);

    public MenuItem updateMenuItem(int menu_item_id, MenuItem menuCategory);

    public List<MenuItem> getAllMenuItems();

    public String deleteMenuItem(int id);
}
