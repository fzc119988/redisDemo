package com.ssm.service.impl;


import com.ssm.model.User;
import com.ssm.service.MailSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailSendServiceImpl implements MailSendService {
	
	@Autowired
	private MailSender mailSender;
	
	/**
	 * 发送邮件
	 */
	public void sendMail(User use) {
		SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("1362981705@qq.com");
        message.setTo(use.getMail());
        message.setSubject(use.getUsername());
        //发送邮件内容
        message.setText(use.getUsername());
        mailSender.send(message);
	}

}
