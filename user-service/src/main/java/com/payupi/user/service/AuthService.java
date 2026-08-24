package com.payupi.user.service;


import com.payupi.user.dto.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

      String register(RegisterRequest request);

      String  verifyEmail(String token);

//    String  login(LoginRequest request);

      void  verifyLoginOtp(String otp, String registrationId, HttpServletResponse response);

     void resendOtp(HttpServletRequest request);
}
