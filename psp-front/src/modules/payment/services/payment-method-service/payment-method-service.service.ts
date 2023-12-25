import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from 'src/modules/shared/service/config-service/config.service';
import { PaymentMethod, UpdatePaymentMethod } from '../../model/payment-method';

@Injectable({
  providedIn: 'root'
})
export class PaymentMethodService {

  constructor(private http: HttpClient, private configService: ConfigService) {}

  getSubscribedPaymentMethods() {
    return this.http.get<PaymentMethod[]>(this.configService.SUBSCRIBED_PAYMENT_METHOD_URL);
  }

  getAllPaymentMethods() {
    return this.http.get<PaymentMethod[]>(this.configService.PAYMENT_METHOD_URL);
  }

  updatePaymentMethods(updatePaymentMethods: UpdatePaymentMethod[]) {
    return this.http.post<PaymentMethod[]>(this.configService.UPDATE_SUBSCRIBED_PAYMENT_METHOD_URL, updatePaymentMethods);
  }
}
