import { Component, OnInit, OnDestroy, ChangeDetectorRef, NgZone } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css']
})
export class DashboardComponent implements OnInit, OnDestroy {

  // Rotating search bar placeholder texts
  searchPlaceholders: string[] = [
    'Pay by name or phone number',
    'Pay friends and merchants',
    'Pay anyone on UPI'
  ];
  currentPlaceholderIndex = 0;
  currentPlaceholder = this.searchPlaceholders[0];
  private placeholderInterval: any;

  // Mock current user - replace with real user service later
  currentUser = {
    name: 'User',
    initial: 'U'
  };

  constructor(
    private router: Router,
    private cdr: ChangeDetectorRef,
    private ngZone: NgZone
  ) {}

  ngOnInit(): void {
    this.startPlaceholderRotation();
  }

  ngOnDestroy(): void {
    if (this.placeholderInterval) {
      clearInterval(this.placeholderInterval);
    }
  }

  private startPlaceholderRotation(): void {
    // Run the interval outside Angular's zone for performance,
    // then explicitly re-enter the zone (or trigger detectChanges)
    // whenever we actually update the bound property.
    this.ngZone.runOutsideAngular(() => {
      this.placeholderInterval = setInterval(() => {
        this.currentPlaceholderIndex =
          (this.currentPlaceholderIndex + 1) % this.searchPlaceholders.length;
        this.currentPlaceholder = this.searchPlaceholders[this.currentPlaceholderIndex];

        // Tell Angular to re-render the view with the new value
        this.ngZone.run(() => {
          this.cdr.detectChanges();
        });
      }, 3000);
    });
  }

  // Top right user icon click -> navigate to user details page
  goToUserDetails(): void {
    this.router.navigate(['/user-details']);
  }

  // "Add bank account" button click -> navigate to add bank account page
  goToAddBankAccount(): void {
    this.router.navigate(['/addBanks']);
  }

  // Bottom nav: Home (current page, no navigation needed)
  goToHome(): void {
    this.router.navigate(['/dashboard']);
  }

  // Bottom nav: Money icon -> navigate to money page
  goToMoney(): void {
    this.router.navigate(['/money']);
  }

  // Bottom nav: You icon -> navigate to user details page
  goToYou(): void {
    this.router.navigate(['/login']);
  }

  // Generic action handler placeholders for quick action icons
  onScanQrCode(): void {
    // TODO: implement navigation to scan QR page
  }

  onPayAnyone(): void {
    // TODO: implement navigation to pay anyone page
  }

  onBankTransfer(): void {
    // TODO: implement navigation to bank transfer page
  }

  onMobileRecharge(): void {
    // TODO: implement navigation to mobile recharge page
  }
}