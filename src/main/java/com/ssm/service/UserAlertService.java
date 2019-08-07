package com.ssm.service;

import com.ssm.model.ConfirmEmail;
import com.ssm.model.User;

import javax.mail.MessagingException;

public interface UserAlertService {
    void loginUser(User user);

}
