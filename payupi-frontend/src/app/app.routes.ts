import { Routes } from '@angular/router';

import { WelcomeComponent } from './MyComponents/welcome/welcome';
import { LoginComponent } from './MyComponents/login/login';
import { OtpVerificationComponent } from './MyComponents/otpverification/otpverification';
import { DashboardComponent } from './MyComponents/dashboard/dashboard';
import { BankListsComponent } from './MyComponents/addbanks/addbanks';

import { authGuard } from './guards/auth.guard';

export const routes: Routes = [

// Public routes
{ path: '', component: WelcomeComponent },
{ path: 'login', component: LoginComponent },


// Protected routes
{
path: 'dashboard',
component: DashboardComponent,
canActivate: [authGuard]
},

{
path: 'addBanks',
component: BankListsComponent,
canActivate: [authGuard]
},

{
path: 'otp-verification',
component: OtpVerificationComponent,
canActivate: [authGuard]
}




// { path: 'bank-details/:id', component: BankDetailsComponent }
];
