package com.ssafy.BackEnd.service;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public interface EmailService {
    void sendMail(String to, String sub, String text) throws MessagingException;
}
