package com.example.services.mail;

import java.io.IOException;

import javax.mail.MessagingException;

public interface SendMailService {
    void sendEmail(String to, String body, String topic) throws MessagingException, IOException;
}
