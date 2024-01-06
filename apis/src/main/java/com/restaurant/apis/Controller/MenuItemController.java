package com.restaurant.apis.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.apis.Model.MenuItem;
import com.restaurant.apis.Service.MenuItemService;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
@RequestMapping("/menu-items")
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        super();
        this.menuItemService = menuItemService;
    }

    @PostMapping("addMenuItems")
    public List<MenuItem> addMenuItems(@RequestBody List<MenuItem> menuItemList) {
        return menuItemService.addMenuItems(menuItemList);
    }

    @GetMapping("getAllMenuItems")
    public List<MenuItem> getAllMenuItems() {
        return menuItemService.getAllMenuItems();
    }

    @PostMapping("updateMenuItem")
    public MenuItem updaMenuItem(@RequestParam int menu_item_id, @RequestBody MenuItem menuItem) {
        return menuItemService.updateMenuItem(menu_item_id, menuItem);
    }

    @DeleteMapping("deleteMenuItem")
    public ResponseEntity<String> deleteMenuItem(@RequestParam int menu_item_id) {
        String res = menuItemService.deleteMenuItem(menu_item_id);
        return new ResponseEntity<String>(res, HttpStatus.OK);
    }
}