package com.restaurant.apis.Service;

import java.util.List;

import com.restaurant.apis.Model.MenuItem;

import jakarta.transaction.Transactional;

@Transactional
public interface MenuItemService {
    public List<MenuItem> addMenuItems(List<MenuItem> menuItemList);

    public MenuItem updateMenuItem(int menu_item_id, MenuItem menuCategory);

    public List<MenuItem> getAllMenuItems();

    public String deleteMenuItem(int id);
}
