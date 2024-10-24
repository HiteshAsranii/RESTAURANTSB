package com.restaurant.apis.Service.Implementation;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.apis.Model.MenuCategory;
import com.restaurant.apis.Service.MenuCategoryService;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class MenuCategoryServiceImplementation implements MenuCategoryService {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<MenuCategory> addMenuCategory(List<MenuCategory> menuCategoryList) {
        for (MenuCategory m : menuCategoryList) {
            entityManager.persist(m);
        }
        return menuCategoryList;
    }

    @Override
    public MenuCategory updateMenuCategory(int menu_category_id, MenuCategory menuCategory) {
        MenuCategory oldMenuCategory = entityManager.find(MenuCategory.class, menu_category_id);
        if(oldMenuCategory != null){
            oldMenuCategory.setMenuCategoryName(menuCategory.getMenuCategoryName());
            entityManager.merge(oldMenuCategory);
        }
        return menuCategory;
    }

    @Override
    public List<MenuCategory> getAllMenuCategories() {
        List<MenuCategory> menuCategories = entityManager.createQuery("Select mc from MenuCategory mc", MenuCategory.class).getResultList();
        return menuCategories;
        
    }

    @Override
    public String deleteMenuCategory(int id){
        MenuCategory mc = entityManager.find(MenuCategory.class, id);
        if(mc != null){
            entityManager.remove(mc);
            return "Menu Category Removed Successfully";
        }
        return "Some Error Occurred";
    }  

}
