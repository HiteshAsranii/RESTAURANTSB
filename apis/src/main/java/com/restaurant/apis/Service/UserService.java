package com.restaurant.apis.Service;


import com.restaurant.apis.Model.User;

import jakarta.transaction.Transactional;

@Transactional
public interface UserService {
    public User createUser(User user);
}
