package com.lyh.community.controller;

import com.lyh.community.entity.Sms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/sms")
public class SmsController {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Value("${spring.mail.username}")
    private String from;

    @RequestMapping(value = "/sendEmailCode",method = RequestMethod.POST)
    private void sendEmailCode(String email){
        String ZiMu = "1234567890";
        String code = "";
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(ZiMu.length());
            char c = ZiMu.charAt(index);
            code = code + c;
        }
        redisTemplate.opsForValue().set(email,code,10, TimeUnit.MINUTES);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("ReUpCommunity验证码");
        message.setText("验证码: "+code+" ，请在10分钟内验证!");
        message.setTo(email);
        message.setFrom(from);
        mailSender.send(message);
    }
}
