import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PaymentRequest, RedirectInfo } from '../../model/payment-request';
import { ConfigService } from '../../../shared/service/config-service/config.service';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  constructor(private http: HttpClient, private configService: ConfigService) {}

  payment(paymentRequest: PaymentRequest) {
    return this.http.post<any>(this.configService.PSP_URL, paymentRequest,
      {observe: 'response'});

  }
}
