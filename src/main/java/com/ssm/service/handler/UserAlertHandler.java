package com.ssm.service.handler;

import com.rabbitmq.client.Channel;
import com.ssm.model.User;
import com.ssm.service.MailSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.SerializationUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;


public class UserAlertHandler implements ChannelAwareMessageListener ,Serializable{

    private static final Logger log = LoggerFactory.getLogger(UserAlertHandler.class);

    @Autowired
    private MailSendService mailSendService;

    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            if (StringUtils.isEmpty(new String(message.getBody(), "UTF-8"))) {
                return;
            }
            User user = (User) SerializationUtils.deserialize(message.getBody());
            log.info("消费者消费{}", user);
            //发送邮件
            mailSendService.sendMail(user);
            //手动确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}




