import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { BankService, Bank } from '../../services/bank.service';

interface TopBank {
  bankName: string;
  color: string;
  initial: string;
}

@Component({
  selector: 'app-bank-lists',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './addbanks.html',
  styleUrls: ['./addbanks.css']
})
export class BankListsComponent implements OnInit {

  loading = true;
  error = '';

  banks: Bank[] = [];
  filteredBanks: Bank[] = [];

  searchText = '';

  topBanks: TopBank[] = [
    {
      bankName: 'State Bank of India',
      initial: 'S',
      color: '#00AEEF'
    },
    {
      bankName: 'HDFC Bank',
      initial: 'H',
      color: '#0033A0'
    },
    {
      bankName: 'ICICI Bank',
      initial: 'I',
      color: '#F58220'
    },
    {
      bankName: 'Axis Bank',
      initial: 'A',
      color: '#97144D'
    },
    {
      bankName: 'Punjab National Bank',
      initial: 'P',
      color: '#F4B400'
    },
    {
      bankName: 'Bank of Baroda',
      initial: 'B',
      color: '#F36F21'
    }
  ];

  avatarColors: string[] = [
    '#4285F4',
    '#DB4437',
    '#F4B400',
    '#0F9D58',
    '#7E57C2',
    '#26A69A',
    '#5C6BC0',
    '#EF6C00',
    '#EC407A',
    '#26C6DA'
  ];

  constructor(
    private bankService: BankService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadBanks();
  }

  loadBanks(): void {

    this.loading = true;

    this.bankService.getAllBanks().subscribe({

      next: (response) => {

        this.banks = response;

        this.filteredBanks = [...response];

        this.loading = false;
      },

      error: (err) => {

        console.error(err);

        this.error = 'Unable to load banks';

        this.loading = false;
      }

    });

  }

  onSearch(): void {

    const value = this.searchText.trim().toLowerCase();

    if (!value) {

      this.filteredBanks = [...this.banks];

      return;

    }

    this.filteredBanks = this.banks.filter(bank =>
      bank.bankName.toLowerCase().includes(value)
    );

  }

  clearSearch(): void {

    this.searchText = '';

    this.filteredBanks = [...this.banks];

  }

  getInitial(bankName: string): string {

    if (!bankName) {

      return '?';

    }

    return bankName.charAt(0).toUpperCase();

  }

  getAvatarColor(bankName: string): string {

    let hash = 0;

    for (let i = 0; i < bankName.length; i++) {

      hash = bankName.charCodeAt(i) + ((hash << 5) - hash);

    }

    return this.avatarColors[Math.abs(hash) % this.avatarColors.length];

  }

  onBankClick(bank: Bank): void {

    console.log('Selected Bank : ', bank);

    // Navigate to next page

    // this.router.navigate(['/bank-details', bank.id]);

  }

  onTopBankClick(topBank: TopBank): void {

    const bank = this.banks.find(
      b => b.bankName.toLowerCase() === topBank.bankName.toLowerCase()
    );

    if (bank) {

      this.onBankClick(bank);

    }

  }

  goBack(): void {

    this.router.navigate(['/']);

  }




   getBankLogo(name: string): string {

  switch(name){

    case 'State Bank of India':
      return 'sbi.svg';

    case 'HDFC Bank':
      return 'hdfc.png';

    case 'ICICI Bank':
      return 'icici.jpeg';

    case 'Axis Bank':
      return 'axis.jpeg';

    case 'Punjab National Bank':
      return 'pnb.jpeg';

    case 'Bank of Baroda':
      return 'bob.jpeg';

    case 'Yes Bank':
      return 'yes.png';

    case 'Airtel Payments Bank':
      return 'airtelPay.jpeg';

    case 'AU Small Finance Bank':
          return 'AU.png';
    
    case 'Canara Bank':
         return 'canara.jpeg';
    
    case 'Central Bank Of India':
         return 'central.png';

    case 'India Post Payment Bank':
          return 'post.jpeg';

    case 'Paytm Payments Bank':
          return 'paytm.jpeg';

    case 'Kotak Mahindra Bank':
          return 'kotak.png';

    case 'Bandhan Bank':
          return 'Bandhan.jpeg';     
          
    case 'Union Bank of India':
          return 'union.png';

   default:
      return 'default.png';
  }

}

}