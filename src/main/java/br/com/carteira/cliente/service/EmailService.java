package br.com.carteira.cliente.service;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class EmailService {

	@Autowired
	private JavaMailSender emailSender;

	@Value("${spring.mail.username}")
	String emailFrom;

	public void sendSimpleMessage(String to, String subject, String text) {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(emailFrom);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);

		emailSender.send(message);
	}

	public void sendEmailWithAttachments(String to, String subject, String text, List<MultipartFile> attachments)
			throws MessagingException, IOException {

		MimeMessage message = emailSender.createMimeMessage();

		MimeMessageHelper mimeHelper = new MimeMessageHelper(message, true);
		mimeHelper.setFrom(emailFrom);
		mimeHelper.setTo(to);
		mimeHelper.setSubject(subject);
		mimeHelper.setText(text);

		if (attachments != null) {
			for (MultipartFile file : attachments) {
				mimeHelper.addAttachment(file.getOriginalFilename(),
						new ByteArrayDataSource(file.getInputStream(), "application/octet-stream"));
			}
		}

		emailSender.send(message);
	}
}
