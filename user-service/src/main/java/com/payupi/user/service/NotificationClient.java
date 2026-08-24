package com.payupi.user.service;


import com.payupi.user.dto.EmailRequest;
import com.payupi.user.dto.OtpRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "notification-service",
        url = "http://localhost:8081"
)
public interface NotificationClient {

    @PostMapping("/notifications/email")
    String sendEmail(
            @RequestBody EmailRequest request);

    @PostMapping("/notifications/otp")
    String sendOtp(
            @RequestBody OtpRequest request
            );
}
