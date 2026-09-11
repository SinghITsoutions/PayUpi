import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { StartRegistrationRequest } from '../models/start-registration-request';
import { StartRegistrationResponse } from '../models/start-registration-response';

// ── Request / Response models ──────────────────────────────────────────────────

// export interface OtpRequest {
//   phoneNumber: string;   // e.g. "+919244170200"
// }

// export interface OtpResponse {
//   success: boolean;
//   message: string;
//   requestId?: string;    // optional token returned by Spring Boot to track OTP session
// }

// ── Service ────────────────────────────────────────────────────────────────────

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  
  // private readonly BASE_URL = 'http://localhost:8080';

   /** Full endpoint path that matches your Spring Boot @RequestMapping */
  // private readonly SEND_OTP_URL = `${this.BASE_URL}/api/auth/send-otp`;

  // private headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  private apiUrl ='http://localhost:8080/auth';
  private apiUrl2 ='http://localhost:8080';

  constructor(private http: HttpClient) {}

  startRegistration(
    mobileNumber: string
  ): Observable<StartRegistrationResponse> {

    const request: StartRegistrationRequest = {
      mobileNumber
    };

    return this.http.post<StartRegistrationResponse>(
      `${this.apiUrl}/start-registration`,
      request,
      {
        withCredentials:true
      }
    );
  }

  /**
   * POST /api/auth/send-otp
   *
   * Sends the user's phone number to the Spring Boot controller.
   * Spring Boot side should have something like:
   *
   *   @RestController
   *   @RequestMapping("/api/auth")
   *   public class AuthController {
   *
   *     @PostMapping("/send-otp")
   *     public ResponseEntity<OtpResponse> sendOtp(@RequestBody OtpRequest request) {
   *       // generate & send OTP via SMS gateway
   *       return ResponseEntity.ok(new OtpResponse(true, "OTP sent", requestId));
   *     }
   *   }
   */
  // sendOtp(phoneNumber: string): Observable<OtpResponse> {
  //   const body: OtpRequest = { phoneNumber };
  //   return this.http.post<OtpResponse>(this.SEND_OTP_URL, body, { headers: this.headers });
  // }

  /**
   * POST /api/auth/verify-otp
   * (Ready for the next screen — OTP verification)
   */

  // verifyOtp(phoneNumber: string, otp: string, requestId?: string): Observable<any> {
  //   const body = { phoneNumber, otp, requestId };
  //   return this.http.post(`${this.BASE_URL}/api/auth/verify-otp`, body, { headers: this.headers });
  // }

  /**
   * Step 2 — Redirect browser to Spring OAuth2 login.
   * Spring handles Google authentication and on success:
   *   - Sets "accessToken" cookie  (HttpOnly, Secure, 15 min)
   *   - Sets "refreshToken" cookie (HttpOnly, Secure, 30 days)
   *   - Redirects to http://localhost:4200/dashboard
   *
   * This is a full browser redirect, NOT a fetch/XHR call,
   * because OAuth2 requires browser navigation for the Google consent screen.
   */
  redirectToGoogleLogin(): void {
    window.location.href = `${this.apiUrl2}/oauth2/authorization/google`;
  }
 
  /**
   * OTP flow (welcome screen)
   */
  // sendOtp(phoneNumber: string): Observable<any> {
  //   return this.http.post(
  //     `${this.apiUrl}/auth/start-registration`,
  //     { mobileNumber: phoneNumber },
  //     { headers: this.headers, withCredentials: true }
  //   );
  // }
 
  // verifyLoginOtp(phoneNumber: string, otp: string): Observable<any> {
  //   return this.http.post(
  //     `${this.apiUrl}/auth/verify-login-otp`,
  //     { phoneNumber, otp },
  //     { headers: this.headers, withCredentials: true }
  //   );


   getMobileNumber(): Observable<string> {

  return this.http.get(
    `${this.apiUrl}/mobile-number`,
    {
      responseType: 'text',
     withCredentials: true
    }
  );

}


            
 
         /**
 * Verify OTP.
 */
verifyOtp(otp: string): Observable<any> {

  return this.http.post(
    `${this.apiUrl}/verify-otp`,
    {
      otp
    },
    {
      withCredentials: true
    }
  );

}

         /**
 * Resend OTP.
 */
resendOtp(): Observable<any> {

  return this.http.post(
    `${this.apiUrl}/resend-otp`,
    {},
    {
      withCredentials: true
    }
  );

}

  }

    
