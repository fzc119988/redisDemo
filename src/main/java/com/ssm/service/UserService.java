package com.ssm.service;

import java.util.List;

import com.ssm.model.User;

public interface UserService {

	void saveUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(int id);

    User findUserById(int id);

    List<User> findAll();
}

