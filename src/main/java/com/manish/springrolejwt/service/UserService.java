package com.manish.springrolejwt.service;

import com.manish.springrolejwt.model.User;
import com.manish.springrolejwt.model.UserDto;

import java.util.List;

public interface UserService {
    User save(UserDto user);
    List<User> findAll();
    User findOne(String username);
}
