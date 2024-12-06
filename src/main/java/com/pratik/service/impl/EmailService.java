package com.pratik.service.impl;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.pratik.dto.EmailRequest;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


@Component
public class EmailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String mailFrom;
	
	public void sendEmail(EmailRequest emailReq) throws Exception {
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setFrom(mailFrom,emailReq.getTitle());
		helper.setTo(emailReq.getTo());
		helper.setSubject(emailReq.getSubject());
		helper.setText(emailReq.getMessage(),true);
		
		mailSender.send(message);
		
		
	}

}
