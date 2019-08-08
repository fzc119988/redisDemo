package com.ssm.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.ssm.clent.RedisCache;
import com.ssm.mapper.UserMapper;
import com.ssm.model.User;
import com.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired(required = true)
    private RedisCache redisCache;

    @Autowired
    private UserMapper userMapper;

    public void saveUser(User user) {
        userMapper.saveUser(user);
    }

    public boolean updateUser(User user) {
        int id = user.getId();
        String userId = Integer.toString(id);
        Jedis jedis = redisCache.getResource();
        jedis.del(userId);
        userMapper.updateUser(user);
        String userJson = JSON.toJSONString(user);
        jedis.set(userId, userJson);
        return userMapper.updateUser(user);
    }

    public boolean deleteUser(int id) {
        String userId = Integer.toString(id);
        Jedis jedis = redisCache.getResource();
        jedis.del(userId);
        return userMapper.deleteUser(id);
    }

    public User findUserById(int id) {
        User user = userMapper.findUserById(id);
        return user;
    }

    public List<User> findAll() {
        List<User> allUser = userMapper.findAll();
        return allUser;
    }

}

