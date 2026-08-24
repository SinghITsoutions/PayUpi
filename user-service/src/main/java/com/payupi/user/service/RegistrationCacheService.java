package com.payupi.user.service;

import jakarta.servlet.http.HttpServletResponse;

public interface RegistrationCacheService {
    public String storeMobileNumber(String mobileNumber, HttpServletResponse response);
    public String getMobileNumber(String registrationId);
    public void storeOtp(String registrationId, String otp);
    public String getOtp(String registrationId);
    public void remove(String registrationId);


}
