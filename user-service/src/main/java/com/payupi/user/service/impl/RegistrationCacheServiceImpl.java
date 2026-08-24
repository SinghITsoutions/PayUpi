package com.payupi.user.service.impl;

import com.payupi.user.service.RegistrationCacheService;
import com.payupi.user.util.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationCacheServiceImpl implements RegistrationCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public String storeMobileNumber(String mobileNumber,
                                    HttpServletResponse response) {

        log.info("Saving mobile number '{}' in Redis", mobileNumber);


        String registrationId =
                UUID.randomUUID().toString();

        String key =
                "mobileNumber:" + registrationId;



        redisTemplate.opsForValue().set(
                key,
                mobileNumber,
                Duration.ofMinutes(3)
        );

        CookieUtil.createRegistraionCookie(response,registrationId);

        log.info("Created Registration Cookie XXX: {}", registrationId);

        String value = (String) redisTemplate.opsForValue().get(key);
        log.info("Stored value in Redis: '{}'", value);



        return registrationId;
    }

    @Override
    public String getMobileNumber(String registrationId) {

        String key = "mobileNumber:" + registrationId;

        String mobileNumber = (String) redisTemplate.opsForValue().get(key);

         return mobileNumber;
    }


    public void storeOtp(String registrationId, String otp) {

        String key = "otp:" + registrationId;
        redisTemplate.opsForValue().set(
                key,
                otp,
                Duration.ofMinutes(1)
        );

    }

    public String getOtp(String registrationId) {



        return (String) redisTemplate
                .opsForValue()
                .get("otp:" + registrationId);
    }

    @Override
    public void remove(String registrationId) {

        redisTemplate.delete("otp:" + registrationId);
        redisTemplate.delete("mobileNumber:" + registrationId);

    }


}
