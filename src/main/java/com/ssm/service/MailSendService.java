package com.ssm.service;


import com.ssm.model.User;

public interface MailSendService {
	void sendMail(User user);
}
