package com.upi.notification.controller;

import com.upi.notification.dto.EmailRequest;
import com.upi.notification.dto.OtpRequest;
import com.upi.notification.service.impl.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private  final EmailService emailService;
    @PostMapping("/email")
    public String sendEmail(
            @RequestBody EmailRequest request) {

           System.out.println(request.getLink());

        emailService.sendVerificationEmail(request.getEmail(),
                                           request.getLink());



        return "Email Sent Successfully";
    }

    @PostMapping("/otp")
    public String sendOtp(
           @RequestBody OtpRequest request ){

         emailService.sendVerificationOtp(request.getEmail(), request.getOtp());

         return  "Otp is send Successfully ";
    }
}