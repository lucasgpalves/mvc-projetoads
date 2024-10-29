package com.projeto.ads.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ServiceEmail {
    
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String remetente, String email, String corpo, String assunto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(remetente);
        message.setTo(email);
        message.setSubject(assunto);
        message.setText(corpo);
        javaMailSender.send(message);
    }
}
