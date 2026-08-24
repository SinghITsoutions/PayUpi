
package com.payupi.user.controller;


import com.payupi.user.dto.StartRegistrationRequest;
import com.payupi.user.dto.VerifyOtpRequest;
import com.payupi.user.service.AuthService;
import com.payupi.user.service.RegistrationCacheService;
import com.payupi.user.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {

//    @Autowired
//    private JwtUtil jwtUtil;

      private final AuthService authService;
      private final RegistrationCacheService cacheService;
      private final RegistrationCacheService registrationCacheService;

    @PostMapping("/start-registration")
    public ResponseEntity<?> startRegistration(
            @RequestBody StartRegistrationRequest request,
            HttpServletResponse response) {

        log.info("Received mobile: '{}'", request.getMobileNumber());

        String registrationId = cacheService.storeMobileNumber( request.getMobileNumber(), response );

        return ResponseEntity.ok(Map.of( "registrationId", registrationId)  );
    }

    @GetMapping("/mobile-number")
    public ResponseEntity<String> getMobileNumber(
            HttpServletRequest request) {

        log.info("mobileNumber controller called");

        String registrationId = CookieUtil.getCookieValue(request,"registrationId");

        if (registrationId == null)      return ResponseEntity.badRequest().body("Registration not found ");

        String mobileNumber = registrationCacheService.getMobileNumber(registrationId);

        if (mobileNumber == null)   return ResponseEntity.notFound().build();


        return ResponseEntity.ok(mobileNumber);
    }



    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {

        log.info("Email verification request");
        return ResponseEntity.ok( authService.verifyEmail(token));
    }






    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp (@RequestBody VerifyOtpRequest request,
                                             @CookieValue("registrationId")
                                             String  registrationId, HttpServletResponse response) {

        log.info("verify-otp controller called");

               authService.verifyLoginOtp(request.getOtp(),registrationId,response);
               return ResponseEntity.ok(Map.of("success", true, "message", "welcome"));
    }



   @PostMapping("/resend-otp")
   public ResponseEntity<String> resendOtp(
           HttpServletRequest request
   ) {

        log.info("resend-otp called");
        authService.resendOtp(request);

        return ResponseEntity.ok("OTP send Successfully");
   }



}