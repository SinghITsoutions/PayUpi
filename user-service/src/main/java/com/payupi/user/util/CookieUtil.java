package com.payupi.user.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class CookieUtil {

     public static void createRegistraionCookie(
             HttpServletResponse response,
             String registrationId
     ){
         Cookie cookie =
                 new Cookie("registrationId", registrationId);

         cookie.setHttpOnly(true);
         cookie.setPath("/");
         cookie.setMaxAge(600);
         cookie.setSecure(false);

         response.addCookie(cookie);

     }

     public static void createTokenCookie(HttpServletResponse response,String accessToken,String refreshToken){
         Cookie accessCookie =
                 new Cookie("accessToken", accessToken);

         accessCookie.setHttpOnly(true);
         accessCookie.setSecure(false); // HTTPS only
         accessCookie.setPath("/");
         accessCookie.setMaxAge(15 * 60);

         Cookie refreshCookie =
                 new Cookie("refreshToken", refreshToken);

         refreshCookie.setHttpOnly(true);
         refreshCookie.setSecure(false); //  false is for http and true is for HTTPS only
         refreshCookie.setPath("/");
         refreshCookie.setMaxAge(30 * 24 * 60 * 60);

         response.addCookie(accessCookie);
         response.addCookie(refreshCookie);

     }

    public static String getCookieValue(
            HttpServletRequest request,
            String cookieName) {

        if (request.getCookies() == null) {
            return null;
        }

        for (Cookie cookie : request.getCookies()) {

            if (cookieName.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }

    public static void deleteCookie(
            HttpServletResponse response,
            String cookieName) {

        Cookie cookie = new Cookie(cookieName, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
    }

}
