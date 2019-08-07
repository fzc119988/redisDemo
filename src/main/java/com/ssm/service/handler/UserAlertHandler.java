package com.ssm.service.handler;

import com.alibaba.dubbo.config.annotation.Service;
import com.ssm.model.ConfirmEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
@Service
public class UserAlertHandler {
    private JavaMailSender mailSender;
    @Autowired
    public  UserAlertHandler(JavaMailSender mailSender){
        this.mailSender=mailSender;
    }
    //发送邮件
    public void sendEmailToUser(ConfirmEmail confirmEmail){
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("1362981705@qq.com");
            helper.setTo(confirmEmail.getUser().getMail());
            helper.setSubject("请完成注册");
            helper.setText("hello");
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

}
