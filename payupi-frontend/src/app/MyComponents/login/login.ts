import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css'],
  providers: [AuthService]
})
export class LoginComponent {

  isLoading: boolean = false;
  errorMessage: string = '';

  constructor(private authService: AuthService) {}

  /**
   * Called when user clicks "Continue with Google".
   *
   * Flow:
   * 1. POST /auth/start-registration → Spring sets "registrationId" cookie
   * 2. Redirect browser to /oauth2/authorization/google
   * 3. Spring handles Google OAuth → on success sets "accessToken" +
   *    "refreshToken" cookies and redirects to /dashboard
   *
   * Note: We pass an empty mobileNumber here because the user came
   * directly to the login page. If you want to pass the mobile number
   * from the welcome screen, use a shared service or query param.
   */


  // onGoogleLogin(): void {
  //   this.isLoading = true;
  //   this.errorMessage = '';

  //   // Step 1: set registrationId cookie first
  //   this.authService.startRegistration('').subscribe({
  //     next: () => {
  //       // Step 2: redirect to Spring OAuth2 Google login
  //       this.authService.redirectToGoogleLogin();
  //     },
  //     error: (err) => {
  //       this.isLoading = false;
  //       console.error('Registration start failed:', err);
  //       if (err.status === 0) {
  //         this.errorMessage = 'Cannot connect to server. Is Spring Boot running?';
  //       } else {
  //         this.errorMessage = 'Something went wrong. Please try again.';
  //       }
  //     }
  //   });
  // }

  onGoogleLogin(): void {

  this.isLoading = true;
  this.errorMessage = '';

  this.authService.redirectToGoogleLogin();
}


}