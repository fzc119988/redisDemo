package com.ssm.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ssm.mapper.UserMapper;
import com.ssm.model.User;
import com.ssm.service.UserLogin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserLoginImpl implements UserLogin{
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private UserMapper userMapper;

    //这里将用户对象作为队列消息发送
    public void loginUser(User user) {
        rabbitTemplate.convertAndSend("user.alert.email.exchange", "user.alerts.email",user);
        userMapper.saveUser(user);
    }
}
