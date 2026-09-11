import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Bank {
  id: number;
  bankName: string;
}

@Injectable({
  providedIn: 'root'
})
export class BankService {
  private baseUrl = 'http://localhost:8080/bank';

  constructor(private http: HttpClient) {}

  getAllBanks(): Observable<Bank[]> {
    return this.http.get<Bank[]>(`${this.baseUrl}/getAllBanks`);
  }
}