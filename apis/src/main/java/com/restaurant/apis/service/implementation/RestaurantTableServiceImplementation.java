package com.restaurant.apis.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.apis.model.Orders;
import com.restaurant.apis.model.RestaurantTable;
import com.restaurant.apis.service.RestaurantTableService;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class RestaurantTableServiceImplementation implements RestaurantTableService {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<RestaurantTable> addRestaurantTable(List<RestaurantTable> restaurantTables) {
        for (RestaurantTable r : restaurantTables) {
            entityManager.persist(r);
        }
        return restaurantTables;
    }

    @Override
    public String deleteRestaurantTable(int restraunt_table_id) {
        RestaurantTable r = entityManager.find(RestaurantTable.class, restraunt_table_id);
        if (r != null) {
            entityManager.remove(r);
            return "Table Removed Successfully";
        }
        return "Some error occurred.";
    }

    @Override
    public RestaurantTable updateRestaurantTable(int restraunt_table_id, RestaurantTable restaurantTable) {
        RestaurantTable oldTable = entityManager.find(RestaurantTable.class, restraunt_table_id);
        if (oldTable != null) {
            oldTable.setTableCapacity(restaurantTable.getTableCapacity());
            entityManager.merge(oldTable);
        }
        return oldTable;
    }

    @Override
    public List<RestaurantTable> getAllRestaurantTables() {
        List<RestaurantTable> restaurantTables = entityManager
                .createQuery("Select r from RestaurantTable r", RestaurantTable.class).getResultList();
        return restaurantTables;
    }

    @Override
    public RestaurantTable reserveTable(int restaurant_table_id) {
        RestaurantTable t = entityManager.find(RestaurantTable.class, restaurant_table_id);
        t.setTableStatus(true);
        return t;
    }

    @Override
    public RestaurantTable unReserveTable(int order_id) {
        Orders order = entityManager.find(Orders.class, order_id);
        // Get the associated table
        RestaurantTable table = order.getTableId();
        // Update the table status to unreserved (false)
        table.setTableStatus(false);
        // Merge the changes into the database
        entityManager.merge(table);
        return table;
    }

}
