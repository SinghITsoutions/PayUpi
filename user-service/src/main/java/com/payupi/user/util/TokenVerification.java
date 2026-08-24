package com.payupi.user.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TokenVerification {

         public static String  generateToken()
         {
             // GENERATE EMAIL VERIFICATION TOKEN


                   return   UUID.randomUUID().toString();


         }
}
