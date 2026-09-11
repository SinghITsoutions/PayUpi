import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-welcome',
  standalone: true,
  imports: [FormsModule, CommonModule, HttpClientModule],
  templateUrl: './welcome.html',
  styleUrls: ['./welcome.css'],
  providers: [AuthService]
})
export class WelcomeComponent {

  phoneNumber: string = '';
  isLoading: boolean = false;
  errorMessage: string = '';

  constructor(
    private authService: AuthService,
    public router: Router
  ) {}
  



  onPhoneInput(event: any): void {
  const digitsOnly = event.target.value.replace(/\D/g, '').slice(0, 10);
  this.phoneNumber = digitsOnly;
  event.target.value = digitsOnly;
  this.errorMessage = '';
}

  selectPhone(phone: string): void {
    this.phoneNumber = phone.replace(/\D/g, '');
    this.errorMessage = '';
  }

  isPhoneValid(): boolean {
    return this.phoneNumber.replace(/\s/g, '').length === 10;
  }

  sendMobileNumber(){
     this.authService
      .startRegistration(this.phoneNumber)
      .subscribe({

        next: (response) => {

          console.log(
            'Registration Id:',
            response.registrationId
          );

           this.router.navigate(['login']);

          // localStorage.setItem(
          //   'registrationId',
          //   response.registrationId
          // );
        },

        error: (error) => {
          console.error(error);
        }
      });
  }

 


  // onContinue(): void {
  //   if (!this.isPhoneValid()) {
  //     this.errorMessage = 'Please enter a valid 10-digit phone number.';
  //     return;
  //   }

  //   this.isLoading = true;
  //   this.errorMessage = '';

  //   const fullPhone = '+91' + this.phoneNumber;

  //   this.authService.sendOtp(fullPhone).subscribe({
  //     next: (response) => {
  //       this.isLoading = false;
  //       console.log('OTP sent successfully:', response);
  //     },
  //     error: (err) => {
  //       this.isLoading = false;
  //       console.error('Error from Spring Boot API:', err);
  //       if (err.status === 0) {
  //         this.errorMessage = 'Unable to connect to server. Please try again.';
  //       } else if (err.status === 400) {
  //         this.errorMessage = err.error?.message || 'Invalid phone number.';
  //       } else if (err.status === 429) {
  //         this.errorMessage = 'Too many requests. Please wait and try again.';
  //       } else {
  //         this.errorMessage = 'Something went wrong. Please try again.';
  //       }
  //     }
  //   });
  // }
}