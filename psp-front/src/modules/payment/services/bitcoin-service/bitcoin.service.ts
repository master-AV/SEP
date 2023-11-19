import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from 'src/modules/shared/service/config-service/config.service';
import { PaymentRequest } from '../../model/payment-request';
import { Message } from '../../model/message';
import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root'
})
export class BitcoinService {

  constructor(private http: HttpClient, private configService: ConfigService) {}

  bitcoinPayment(paymentRequest: PaymentRequest) {
    return this.http.post<Message>(this.configService.BITCOIN_URL, paymentRequest);

  }


}
