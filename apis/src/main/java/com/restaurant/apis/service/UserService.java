package com.restaurant.apis.service;


import com.restaurant.apis.model.User;

import jakarta.transaction.Transactional;

@Transactional
public interface UserService {
    public User createUser(User user);
}
