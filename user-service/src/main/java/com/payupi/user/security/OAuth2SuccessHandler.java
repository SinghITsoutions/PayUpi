package com.payupi.user.security;

import com.payupi.user.dto.OtpRequest;
import com.payupi.user.dto.RegisterRequest;
import com.payupi.user.service.AuthService;
import com.payupi.user.service.NotificationClient;
import com.payupi.user.service.RegistrationCacheService;
import com.payupi.user.service.impl.JwtService;
import com.payupi.user.util.CookieUtil;
import com.payupi.user.util.OtpGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


    private final JwtService jwtService;
    private final AuthService authService;
    private final RegistrationCacheService registrationCacheService;
    private final NotificationClient notificationClient;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException {

        OAuth2User oauthUser =
                (OAuth2User) authentication.getPrincipal();

        String email = oauthUser.getAttribute("email");

        String name =  oauthUser.getAttribute("name");

        String registrationId =
                CookieUtil.getCookieValue(
                        request,
                        "registrationId");




        RegisterRequest  registerRequest = new RegisterRequest(name,email,registrationId);

        authService.register(registerRequest);



        String accessToken = jwtService.generateToken(email);
        String refreshToken =
                jwtService.generateRefreshToken(email);

        CookieUtil.createTokenCookie(response,accessToken,refreshToken);

         String otp = OtpGenerator.generateOTP();

         registrationCacheService.storeOtp(
                 registrationId,
                 otp
         );

        OtpRequest otpRequest = new OtpRequest(email,otp);

        notificationClient.sendOtp(otpRequest);


        response.sendRedirect(
                "http://localhost:4200/otp-verification"
        );
    }
}