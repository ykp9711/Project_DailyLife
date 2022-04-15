package com.DailyLife.infra.mail.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@RequiredArgsConstructor
@Service
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;

    public String emailSend(String userEmail) {
        Random rd = new Random();

        StringBuilder authKey = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            authKey.append(rd.nextInt(10));
        }


        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("인증메일입니다.");
        message.setText(authKey.toString());

//        mailSender.send(message);
        return authKey.toString();
    }

}
