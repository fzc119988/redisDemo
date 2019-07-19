package com.ssm.service.impl;
import java.util.List;
import com.alibaba.dubbo.config.annotation.Service;
import com.ssm.service.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import com.ssm.mapper.UserMapper;
import com.ssm.model.User;
import com.ssm.service.UserService;
import org.springframework.cache.annotation.CacheEvict;

@Service
public class UserServiceImpl implements UserService {
    /**
     * ע��UserMapper�ӿ�
     */
    @Autowired(required=true)
    private UserMapper userMapper;

    public void saveUser(User user) {
        userMapper.saveUser(user);
    }

    public boolean updateUser(User user) {
        return userMapper.updateUser(user);
    }


    public boolean deleteUser(int id) {
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

