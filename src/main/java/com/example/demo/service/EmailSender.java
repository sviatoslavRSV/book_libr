package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

//@Service("emailSender")
@Service
public class EmailSender {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired

    JavaMailSender javaMailSender;

    public void sendEmail(String subject, String text) {
        String to = "sviatoslav.riabinin@gmail.com";
        String from = "rsvualeks@gmail.com";
        sendEmail(to, from, subject, text);
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
