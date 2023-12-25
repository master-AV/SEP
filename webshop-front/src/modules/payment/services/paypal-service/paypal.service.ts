import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from 'src/modules/shared/service/config-service/config.service';
import { PaymentRequest, RedirectInfo } from '../../model/payment-request';

@Injectable({
  providedIn: 'root'
})
export class PaypalService {

  constructor(private http: HttpClient, private configService: ConfigService) {}

  paypalPayment(paymentRequest: PaymentRequest) {
    return this.http.post<RedirectInfo>(this.configService.PAYPAL_URL, paymentRequest);

  }
}
