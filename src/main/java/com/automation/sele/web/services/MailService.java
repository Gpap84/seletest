package com.automation.sele.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * This class used as a service for sending email runtime...
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Service
public class MailService {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private SimpleMailMessage preConfiguredMessage;

    /**
     * This method will send compose and send the message
     * */
    public void sendMail(String to, String subject, String body)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    /**
     * This method will send a pre-configured message
     * */
    public void sendPreConfiguredMail(String message)
    {
        SimpleMailMessage mailMessage = new SimpleMailMessage(preConfiguredMessage);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }
}
