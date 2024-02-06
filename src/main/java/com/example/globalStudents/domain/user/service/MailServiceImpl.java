package com.example.globalStudents.domain.user.service;

import com.example.globalStudents.global.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    private final RedisUtil redisUtil;
    @Override
    public void sendFindIdMail(String toEmail, String id) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("globalstudentsumc@gmail.com");
        message.setTo(toEmail);
        message.setSubject("[Global Students: Find ID]");
        message.setText("Your ID: "+blurId(id));

        mailSender.send(message);
    }

    @Override
    public void sendFindPasswordMail(String toEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        String authCode = getAuthCode();
        message.setFrom("globalstudentsumc@gmail.com");
        message.setTo(toEmail);
        message.setSubject("[Global Students: Find Password]");
        message.setText("Code: "+ authCode);

        redisUtil.setDataExpire(toEmail,authCode, 60 * 1L);
        mailSender.send(message);
    }

    public String blurId(String id){
        return "*".repeat(3) + id.substring(3);
    }
    public String getAuthCode() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        return String.format("%06d", number);
    }
}
