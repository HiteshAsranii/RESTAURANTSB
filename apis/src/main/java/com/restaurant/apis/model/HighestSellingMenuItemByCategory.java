package com.restaurant.apis.Model;

import lombok.Data;

@Data
public class HighestSellingMenuItemByCategory {
    private Integer menuItemId;
    private Integer saleCount;
    private Integer menuCategoryId;
    private String menuCategoryName;
    private String menuItemName;

    public HighestSellingMenuItemByCategory(Integer menuItemId, Integer saleCount, Integer menuCategoryId, String menuCategoryName,String menuItemName){
        this.menuItemId = menuItemId;
        this.saleCount = saleCount;
        this.menuCategoryId = menuCategoryId;
        this.menuCategoryName = menuCategoryName;
        this.menuItemName = menuItemName;
        
    }
}
