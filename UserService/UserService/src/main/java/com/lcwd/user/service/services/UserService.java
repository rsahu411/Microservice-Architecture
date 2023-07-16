package com.lcwd.user.service.services;

import com.lcwd.user.service.entities.User;

import java.util.List;

public interface UserService {

    // create user
    User saveUser(User user);

    // to get all user
    List<User> getAllUser();

    // to get specific user by user id
    User getUser(String userId);
}
