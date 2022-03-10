package com.ssafy.BackEnd.service.email;

import javax.mail.MessagingException;

public interface EmailService {
    void sendMail(String to, String sub, String text) throws MessagingException;
}
