import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from 'src/modules/shared/service/config-service/config.service';
import { PaymentRequest } from '../../model/payment-request';

@Injectable({
  providedIn: 'root'
})
export class PaypalService {

  constructor(private http: HttpClient, private configService: ConfigService) {}

  paypalPayment(paymentRequest: PaymentRequest) {
    console.log(paymentRequest);
    return this.http.post(this.configService.PAYPAL_API_URL, paymentRequest);

  }
}
