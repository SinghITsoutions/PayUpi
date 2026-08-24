package com.payupi.user.service.impl;

import com.payupi.user.dto.*;
import com.payupi.user.entity.User;
import com.payupi.user.repository.UserRepository;
import com.payupi.user.service.AuthService;
import com.payupi.user.service.NotificationClient;
import com.payupi.user.service.RegistrationCacheService;
import com.payupi.user.util.*;
import com.payupi.user.util.TokenVerification;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {


    //    private final JwtUtil jwtUtil;
//    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final NotificationClient notificationClient;
    private final RegistrationCacheService registrationCacheService;

    @Override
    public String register(RegisterRequest request) {

        log.info("User registration started for email: {}", request.getEmail());

        if (repository.existsByEmail(request.getEmail())) {
            throw new RuntimeException(
                    "Email already registered");
        }


////         CHECK MOBILE NUMBER EXISTS
//
//        if (repository.existsByMobileNumber(
//                request.getMobileNumber())) {
//
//            throw new RuntimeException( "Mobile number already registered" );
//        }
        log.info("Registration Id: {}", request.getRegistrationId());


        String mobileNumber =
                registrationCacheService
                        .getMobileNumber(
                                request.getRegistrationId());

        log.info("Mobile Number from Redis: {}", mobileNumber);


        if (mobileNumber == null) {
            throw new RuntimeException(
                    "Registration session expired");
        }


        try {
            // generate token
            String verificationToken = TokenVerification.generateToken();


            User user = User.builder()
                    .fullName(request.getFullName())
                    .email(request.getEmail())
                    .mobileNumber(mobileNumber)
//                    .password(passwordEncoder.encode(request.getPassword()))
                    .roles(Set.of("USER"))
                    .enabled(false)
                    .emailVerified(false)
                    .mobileVerified(false)
                    .accountLocked(false)
                    .verificationToken(verificationToken)
                    .build();

            log.debug("Saving user into database");

            User savedUser = repository.save(user);

            log.info("User registered successfull");
            log.info("User Mobile: {}", user.getMobileNumber());




            // SEND EMAIL USING NOTIFICATION SERVICE

            EmailRequest emailRequest =
                    new EmailRequest(
                            user.getEmail(), "http://localhost:8080/auth/verify-email?token=" + verificationToken
                    );


            try {
                notificationClient.sendEmail(emailRequest);

                log.info(
                        "Verification email sent");

            } catch (Exception e) {

                log.error(
                        "Notification service error: {}",
                        e.getMessage());
            }


            return "User Registered Successfully";

        } catch (Exception e) {
            log.error("Error occurred during user registration", e);

            throw e;
        }
    }


//    // VERIFY EMAIL

    @Override
    public String  verifyEmail(String token) {

        User user = repository
                .findByVerificationToken(token)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Invalid Token"));

        user.setEmailVerified(true);

        user.setEnabled(true);

        user.setVerificationToken(null);

        repository.save(user);

        log.info("Email verified successfully");

        return "Email Verified Successfully";
    }


//    @Override
//    public String login(LoginRequest request) {
//
//        log.info("Login attempt started for email: {}",
//                request.getEmail());
//
//
//        User user = repository.findByEmail(request.getEmail())
//                .orElseThrow(() -> {
//
//                    log.warn("Login failed. User not found for email: {}",
//                            request.getEmail());
//
//                    return new RuntimeException("User Not Found");
//                });
//
//        log.debug("User found in database: {}",
//                user.getEmail());
//
//
////             Check email verified
//        if (!user.isEmailVerified()) {
//
//            throw new RuntimeException(
//                    "Email not verified");
//        }
//
//        // password check
//        if (!passwordEncoder.matches(
//                request.getPassword(),
//                user.getPassword())) {
//
//            throw new RuntimeException("Invalid password");
//        }
//
//        // generate OTP
//        String otp = OtpGenerate.generateOtp();
//
//        // remove old OTP
//        otpVerificationRepository.deleteByEmail(user.getEmail());
//
//        // save new OTP
//        OtpVerification otpVerification =
//                OtpVerification.builder()
//                        .email(user.getEmail())
//                        .otp(otp)
//                        .expiryTime(
//                                LocalDateTime.now().plusMinutes(5))
//                        .verified(false)
//                        .build();
//
//        otpVerificationRepository.save(otpVerification);
//
//
//        OtpRequest otpRequest = new OtpRequest(user.getEmail(),
//                otp
//        );
//
//        // send OTP email
//        notificationClient.sendOtp(otpRequest);
//
//        return "OTP sent successfully";
//    }


    @Override
    public void verifyLoginOtp(
            String enteredotp,
            String registrationId,
            HttpServletResponse response

    ) {


         String redisOtp = registrationCacheService.getOtp(registrationId);

          if(redisOtp == null) throw new RuntimeException("OTP expired");

          if(!redisOtp.equals(enteredotp)) throw new RuntimeException("Invalid OTP");

          registrationCacheService.remove(registrationId);



    }

    @Override
    public void resendOtp(HttpServletRequest request) {




        String registrationId =
                CookieUtil.getCookieValue(request,"registrationId");

        System.out.println("vishvjeet singh panwar"+registrationId);

        if (registrationId == null) {
            throw new RuntimeException("Registration expired");
        }

        String mobileNumber =
                registrationCacheService.getMobileNumber(registrationId);

        if (mobileNumber == null) {
            throw new RuntimeException("Registration expired");
        }

        User user = repository.findByMobileNumber(mobileNumber)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // Generate new OTP
        String otp = OtpGenerator.generateOTP();

        // Store OTP in Redis (2 minutes)
        registrationCacheService.storeOtp(
                registrationId,
                otp
        );


        log.info("New OTP generated: {}", otp);

        OtpRequest otpRequest = new OtpRequest(user.getEmail(), otp);

        notificationClient.sendOtp(otpRequest);

    }
}
