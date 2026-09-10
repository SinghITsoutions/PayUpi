package com.upi.notification.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendVerificationEmail(
            String toEmail,
            String link ) {

        SimpleMailMessage message =
                new SimpleMailMessage();

        String body =
                "Dear User,\n\n"
                        + "Your verification link  is Here : "
                        + link
                        + "\n\nverify it's you "
                        + "\n\nThank You,\nVishvjeet singh and Team";

        message.setTo(toEmail);
        message.setSubject("Verification Link for PayUpi");
        message.setText(
                body);

        mailSender.send(message);
    }

    public void sendVerificationOtp(
            String toEmail,
            String otp
          ) {

        SimpleMailMessage message =
                new SimpleMailMessage();

        String body =
                "Dear User,\n\n"
                        + "Your Otp for login is Here : "
                        + otp
                        + "\n\nverify otp, it is valid for 2 minutes : "
                        + "\n\nThank You,\nVishvjeet singh and Team";

        message.setTo(toEmail);
        message.setSubject("OTP verification for PayUpi");
        message.setText(
                body);

        mailSender.send(message);
    }
}