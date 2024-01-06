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

import com.restaurant.apis.Model.MenuCategory;
import com.restaurant.apis.Service.MenuCategoryService;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
@RequestMapping("/menu-categories")
public class MenuCategoryController {

    private final MenuCategoryService menuCategoryService;

    public MenuCategoryController(MenuCategoryService menuCategoryService) {
        super();
        this.menuCategoryService = menuCategoryService;
    }

    @PostMapping("/addMenuCategories")
    public List<MenuCategory> addMenuCategory(@RequestBody List<MenuCategory> menuCategoryList) {
        return menuCategoryService.addMenuCategory(menuCategoryList);
    }

    @PostMapping("updateMenuCategory")
    public MenuCategory updMenuCategory(@RequestParam int menu_category_id, @RequestBody MenuCategory menuCategory){
        return menuCategoryService.updateMenuCategory(menu_category_id, menuCategory);    
    }

    @GetMapping("getAllMenuCategories")
    public List<MenuCategory> getAllMenuCategories(){
        return menuCategoryService.getAllMenuCategories();
    }

    @DeleteMapping("deleteMenuCategory")
    public ResponseEntity<String> deleteMenuCategory(@RequestParam int MenuCategoryid){
        String res = menuCategoryService.deleteMenuCategory(MenuCategoryid);
        return new ResponseEntity<String>(res, HttpStatus.OK);        
    }

    
}
