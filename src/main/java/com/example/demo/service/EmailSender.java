package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Locale;

//@Service("emailSender")
@Service
public class EmailSender {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    private MessageSource messageSource;

    public void sendEmail(String subject, String text, String email, Locale local) {
        String to = email;
        sendEmail(to, this.from, subject, text);
    }

    private void sendEmail(String to, String from, String subject, String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        logger.info("sending email...");
        javaMailSender.send(mailMessage);
        logger.info("sent to " + to);
    }
}
