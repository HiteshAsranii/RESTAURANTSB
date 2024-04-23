package com.restaurant.apis.Service;

import java.util.List;

import com.restaurant.apis.Model.RestaurantTable;

import jakarta.transaction.Transactional;

@Transactional
public interface RestaurantTableService {
    public List<RestaurantTable> addRestaurantTable(List<RestaurantTable> restaurantTables);

    public String deleteRestaurantTable(int restraunt_table_id);

    public RestaurantTable updateRestaurantTable(int restraunt_table_id, RestaurantTable restaurantTable);

    public List<RestaurantTable> getAllRestaurantTables();

    public RestaurantTable reserveTable(int restaurant_table_id);

    public RestaurantTable unReserveTable(int restaurant_table_id);

}
