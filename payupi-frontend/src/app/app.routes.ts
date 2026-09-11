import { Routes } from '@angular/router';
import { WelcomeComponent } from './MyComponents/welcome/welcome';
import { LoginComponent }   from './MyComponents/login/login';
import { OtpVerificationComponent } from './MyComponents/otpverification/otpverification';
import { DashboardComponent } from './MyComponents/dashboard/dashboard';
import { BankListsComponent } from './MyComponents/addbanks/addbanks'
 
export const routes: Routes = [
  { path: '', component: WelcomeComponent },
  { path: 'login',component: LoginComponent},
  { path: 'otp-verification',component: OtpVerificationComponent},
  { path: 'dashboard',component: DashboardComponent},
  { path: 'addBanks',component:BankListsComponent},
  // { path: 'bank-details/:id', component: BankDetailsComponent }
  
];