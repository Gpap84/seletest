package com.automation.sele.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * This class used as a service for sending email at runtime...
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Service
public class MailService {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private SimpleMailMessage alertMailMessage;

    public void sendMail(String from, String to, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);

    }

    public void sendAlertMail(String alert) {

        SimpleMailMessage mailMessage = new SimpleMailMessage(alertMailMessage);
        mailMessage.setText(alert);
        mailSender.send(mailMessage);

    }

}
