package com.restaurant.apis.service.implementation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.apis.model.Roles;
import com.restaurant.apis.model.User;
import com.restaurant.apis.model.UserRoles;
import com.restaurant.apis.service.UserService;

import jakarta.persistence.EntityManager;

@Service
public class UserServiceImplementation implements UserService {


    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public User createUser(User user) {
        // Default role setup
        Roles defaultRole = new Roles();
        defaultRole.setRoleId(1); // Assuming 1 is the default role ID


        // Save the user
        entityManager.persist(user);

        // Assign userRole
        UserRoles userRole = new UserRoles();
        userRole.setUserId(user);
        userRole.setRoleId(defaultRole);

        // Save the UserRole record
        entityManager.persist(userRole);

        return user;
    }
}
