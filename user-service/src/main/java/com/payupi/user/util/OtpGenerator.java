package com.payupi.user.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class OtpGenerator {


    private static final SecureRandom random = new SecureRandom();

    private OtpGenerator() {
        // Prevent instantiation
    }

    public static String generateOTP() {

        int otp = 100000 + random.nextInt(900000);

        return String.valueOf(otp);
    }
}