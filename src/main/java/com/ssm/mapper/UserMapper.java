package com.ssm.mapper;

import com.ssm.model.User;

import java.util.List;


public interface UserMapper {

    void saveUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(int id);

    User findUserById(int id);

    List<User> findAll();
}

