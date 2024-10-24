package com.restaurant.apis.service;

import java.util.List;

import com.restaurant.apis.model.MenuCategory;

import jakarta.transaction.Transactional;

@Transactional
public interface MenuCategoryService {
    public List<MenuCategory> addMenuCategory(List<MenuCategory> menuCategoryList);

    public MenuCategory updateMenuCategory(int menu_category_id, MenuCategory menuCategory);

    public List<MenuCategory> getAllMenuCategories();

    public String deleteMenuCategory(int id);
}
