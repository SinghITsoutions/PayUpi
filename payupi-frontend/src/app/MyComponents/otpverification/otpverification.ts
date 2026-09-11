import {
  Component,
  OnInit,
  OnDestroy,
  ViewChildren,
  QueryList,
  ElementRef,
  inject,
  ChangeDetectorRef
} from '@angular/core';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { Subscription, interval } from 'rxjs';
import { take } from 'rxjs/operators';

import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-otp-verification',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './otpverification.html',
  styleUrls: ['./otpverification.css']
})
export class OtpVerificationComponent implements OnInit, OnDestroy {

  @ViewChildren('otpInput')
  otpInputs!: QueryList<ElementRef<HTMLInputElement>>;

  private authService = inject(AuthService);
  private router = inject(Router);
  private cdr = inject(ChangeDetectorRef);

  // -----------------------------
  // Mobile Number
  // -----------------------------

  phoneNumber = '';
  displayPhone = 'Loading...';

  // -----------------------------
  // OTP
  // -----------------------------

  otpDigits: string[] = ['', '', '', '', '', ''];

  // -----------------------------
  // Timer
  // -----------------------------

  timerSeconds = 60;
  timerDisplay = '01:00';
  canResend = false;

  private timerSubscription?: Subscription;

  // -----------------------------
  // UI
  // -----------------------------

  isLoading = false;
  isResending = false;

  errorMessage = '';
  successMessage = '';

  // =====================================================
  // INIT
  // =====================================================

  ngOnInit(): void {

    console.log("OTP component initialized"); //k
    this.loadMobileNumber();

  }

  ngOnDestroy(): void {

    this.timerSubscription?.unsubscribe();

  }

  // =====================================================
  // LOAD MOBILE NUMBER
  // =====================================================

  loadMobileNumber(): void {

    console.log("calling getMObileNUmber");

    this.authService.getMobileNumber().subscribe({

      next: (mobile) => {

         console.log("SUCCESS");
         console.log(mobile);

        this.phoneNumber = mobile;

        this.displayPhone =
          this.formatPhoneNumber(mobile);
          this.cdr.detectChanges();
        this.startTimer();

      },

      error: (err) => {

        this.router.navigate(['/login']);

    }

    });

  }

  // =====================================================
  // TIMER
  // =====================================================

  startTimer(): void {

    this.timerSubscription?.unsubscribe();

    this.timerSeconds = 60;

    this.canResend = false;

    this.updateTimer();

    this.timerSubscription = interval(1000)
      .pipe(take(61))
      .subscribe({

        next: () => {

          this.timerSeconds--;

          this.updateTimer();
             this.cdr.detectChanges();

          if (this.timerSeconds <= 0) {

            this.canResend = true;
             this.cdr.detectChanges();

          }

        }

      });

  }

  updateTimer(): void {

    const minutes =
      Math.floor(this.timerSeconds / 60);

    const seconds =
      this.timerSeconds % 60;

    this.timerDisplay =
      `${minutes.toString().padStart(2, '0')}:${seconds
        .toString()
        .padStart(2, '0')}`;

  }

  


  onKeyDown(event: KeyboardEvent, index: number): void {
  event.preventDefault(); // block browser from typing into box

  const key = event.key;

  // Allow only digits
  if (/^\d$/.test(key)) {
    this.otpDigits[index] = key;
    this.errorMessage = '';

    if (index < 5) {
      this.focus(index + 1);
    }

    if (this.isOtpComplete()) {
      this.verifyOtp();
    }
    return;
  }

  if (key === 'Backspace') {
    if (this.otpDigits[index] !== '') {
      this.otpDigits[index] = '';
    } else if (index > 0) {
      this.otpDigits[index - 1] = '';
      this.focus(index - 1);
    }
    return;
  }

  if (key === 'ArrowLeft' && index > 0) {
    this.focus(index - 1);
    return;
  }

  if (key === 'ArrowRight' && index < 5) {
    this.focus(index + 1);
    return;
  }
}

 


  

  onPaste(event: ClipboardEvent): void {

    event.preventDefault();

    const pasted =
      event.clipboardData
        ?.getData('text')
        .replace(/\D/g, '')
        .slice(0, 6) ?? '';

    pasted.split('').forEach((digit, index) => {

      this.otpDigits[index] = digit;

    });

    if (pasted.length > 0) {

      this.focus(
        Math.min(pasted.length, 5)
      );

    }

    if (this.isOtpComplete()) {

      this.verifyOtp();

    }

  }

  // =====================================================
  // VERIFY OTP
  // =====================================================

  verifyOtp(): void {

    if (!this.isOtpComplete()) {

      return;

    }

    if (this.isLoading) {

      return;

    }

    this.isLoading = true;

    this.errorMessage = '';

    this.authService
      .verifyOtp(this.getOtp())
      .subscribe({

        next: (response) => {

          this.isLoading = false;

          if (response.success) {

            this.successMessage =
              'Phone number verified successfully';

            setTimeout(() => {

              this.router.navigate(['/dashboard']);

            }, 1000);

          } else {

            this.errorMessage =
              response.message ??
              'Invalid OTP';

            this.clearOtp();

          }

        },

        error: () => {

          this.isLoading = false;

          this.errorMessage =
            'Invalid OTP';

          this.clearOtp();

        }

      });

  }

  // =====================================================
  // RESEND OTP
  // =====================================================

  resendOtp(): void {

    if (!this.canResend) {

      return;

    }

    this.isResending = true;

    this.errorMessage = '';

    this.authService
      .resendOtp()
      .subscribe({

        next: (response) => {

          this.isResending = false;

          if (response.success) {

            this.clearOtp();

            this.startTimer();

          } else {

            this.errorMessage =
              response.message ??
              'Unable to resend OTP';

          }

        },

        error: () => {

          this.isResending = false;

          this.errorMessage =
            'Unable to resend OTP';

        }

      });

  }

  // =====================================================
  // HELPERS
  // =====================================================

  getOtp(): string {

    return this.otpDigits.join('');

  }

  isOtpComplete(): boolean {

    return this.otpDigits.every(
      digit => digit !== ''
    );

  }

  clearOtp(): void {

    this.otpDigits = [
      '',
      '',
      '',
      '',
      '',
      ''
    ];

    this.focus(0);

  }

  focus(index: number): void {

    setTimeout(() => {

      this.otpInputs
        ?.toArray()[index]
        ?.nativeElement
        .focus();

    });

  }



  formatPhoneNumber(phone: string): string {

    const number =
      phone.replace(/\D/g, '');

    if (number.length === 10) {

      return `+91 ${number.substring(0, 5)} ${number.substring(5)}`;

    }

    return phone;

  }

  goBack(): void {

    this.router.navigate(['/login']);

  }

}