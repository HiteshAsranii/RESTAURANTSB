package com.restaurant.apis.Service.Implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.apis.Model.MenuItem;
import com.restaurant.apis.Service.MenuItemService;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class MenuItemServiceImplementation implements MenuItemService {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<MenuItem> addMenuItems(List<MenuItem> menuItemList) {
        for (MenuItem m : menuItemList) {
            entityManager.persist(m);
        }
        return menuItemList;
    }

    @Override
    public MenuItem updateMenuItem(int menu_item_id, MenuItem menuItem) {
        MenuItem oldMenuItem = entityManager.find(MenuItem.class, menu_item_id);
        if (oldMenuItem != null) {
            oldMenuItem.setMenuItemName(menuItem.getMenuItemName());
            oldMenuItem.setMenuItemPrice(menuItem.getMenuItemPrice());
            oldMenuItem.setAvailable(menuItem.isAvailable());
            oldMenuItem.setMenuItemDescription(menuItem.getMenuItemDescription());
            oldMenuItem.setMenuCategoryId(menuItem.getMenuCategoryId());
            entityManager.merge(oldMenuItem);
        }
        return menuItem;
    }

    @Override
    public List<MenuItem> getAllMenuItems() {
        List<MenuItem> menuItems = entityManager
                .createQuery("Select mi from MenuItem mi", MenuItem.class).getResultList();
        return menuItems;

    }

    @Override
    public String deleteMenuItem(int id) {
        MenuItem mi = entityManager.find(MenuItem.class, id);
        if (mi != null) {
            entityManager.remove(mi);
            return "Menu Item Removed Successfully";
        }
        return "Some Error Occurred";
    }

}
