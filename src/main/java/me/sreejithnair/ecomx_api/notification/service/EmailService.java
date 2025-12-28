package me.sreejithnair.ecomx_api.notification.service;

import jakarta.mail.MessagingException;

import java.util.Map;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
    void sendHtmlEmail(String to, String subject, String templateName, Map<String, Object> variables) throws MessagingException;
}
