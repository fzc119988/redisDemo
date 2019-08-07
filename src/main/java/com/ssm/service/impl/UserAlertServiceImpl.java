package com.ssm.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ssm.mapper.UserMapper;
import com.ssm.model.ConfirmEmail;
import com.ssm.model.User;
import com.ssm.service.UserAlertService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class UserAlertServiceImpl implements UserAlertService {
    private RabbitTemplate rabbit;
    private UserMapper userMapper;

    @Autowired(required = true)
    public UserAlertServiceImpl(RabbitTemplate rabbit,UserMapper userMapper) {
        this.rabbit = rabbit;
        this.userMapper = userMapper;
    }

    //    将发送邮件任务存入消息队列
    public void sendEmailToUserQueue(ConfirmEmail confirmEmail) {
        //convertAndSend(String exchange,String routingKey,Object object,将对象object封装成Message对象后，发送给exchange)
        rabbit.convertAndSend("user.alert.email.exchange", "user.alerts.email", confirmEmail);
    }

    public void loginUser(User user) {
        userMapper.saveUser(user);
        //3.发送确认邮件
        ConfirmEmail confirmEmail = new ConfirmEmail(user);
        sendEmailToUserQueue(confirmEmail);

    }
}
