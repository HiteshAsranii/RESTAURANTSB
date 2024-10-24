package com.restaurant.apis.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.apis.Model.RestaurantTable;
import com.restaurant.apis.Service.RestaurantTableService;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
@RequestMapping("restaurant-tables")
public class RestaurantTableController {

    private final RestaurantTableService restaurantTableService;

    public RestaurantTableController(RestaurantTableService restaurantTableService){
        this.restaurantTableService = restaurantTableService;
    }

    @PostMapping("addRestaurantTable")
    public List<RestaurantTable> addRestaurantTable(@RequestBody List<RestaurantTable> restaurantTables){
        return restaurantTableService.addRestaurantTable(restaurantTables);
    }
    
    @DeleteMapping("deleteRestaurantTable")
    public String deleteRestaurantTable(@RequestParam int restaurant_table_id){
        return restaurantTableService.deleteRestaurantTable(restaurant_table_id);
    }

    @PostMapping("updateRestaurantTable")
    public RestaurantTable updateRestaurantTable(@RequestParam int restaurant_table_id, @RequestBody RestaurantTable restaurantTable){
        return restaurantTableService.updateRestaurantTable(restaurant_table_id, restaurantTable);
    }

    @GetMapping("getAllRestaurantTables")
    public List<RestaurantTable> getAllRestaurantTables(){
        return new ArrayList<>(restaurantTableService.getAllRestaurantTables());
    }

    
}
