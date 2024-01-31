import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from 'src/modules/shared/service/config-service/config.service';
import { Transaction } from '../../model/transaction';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TransactionsService {

  constructor(private httpClient: HttpClient, private configService: ConfigService) {}

  getAll(): Observable<Transaction[]> {
    return this.httpClient.get<Transaction[]>(this.configService.TRANSACTIONS_URL);
  }

  getById(id: number): Observable<Transaction[]> {
    return this.httpClient.get<Transaction[]>(this.configService.getTransactionsUrl(id));
  }
}
