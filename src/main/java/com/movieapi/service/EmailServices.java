package com.movieapi.service;

import javax.security.auth.Subject;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.movieapi.dto.MailBody;

@Service
public class EmailServices {

	private final JavaMailSender mailSender;
	
	public EmailServices(JavaMailSender mailSender) {
		this.mailSender=mailSender;
	}
	
	
	public void sendSimpleMessage(MailBody mailBody) {
		SimpleMailMessage message= new SimpleMailMessage();
		message.setTo(mailBody.to());
		message.setFrom("praveenamaresh28@gmail.com");
		message.setSubject(mailBody.text());
		message.setText(mailBody.text());
		
		mailSender.send(message);
		
	}
}
