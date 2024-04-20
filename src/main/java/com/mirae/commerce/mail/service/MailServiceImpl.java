package com.mirae.commerce.mail.service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;
    private static final String SENDER_EMAIL = "ssafywoals@gmail.com";
    private static final String SENDER_NAME = "Mirae Mail Sender";
    @Override
    public MimeMessage createMessage(String recipientEmail, String title, String htmlContent) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, recipientEmail);
        message.setSubject(title);
        message.setText(htmlContent, "utf-8", "html");
        message.setFrom(new InternetAddress(SENDER_EMAIL, SENDER_NAME));

        return message;
    }

    @Override
    public void sendMail(MimeMessage message) {
        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
