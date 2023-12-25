import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {

  constructor() {}

  ///PAYPAL
  PAYPAL_API_URL = environment.paypalApiUrl;
  PAYPAL_URL = `${this.PAYPAL_API_URL}/paypal`;

  ///BITCOIN
  BITCOIN_API_URL = environment.bitcoinApiUrl;
  BITCOIN_URL = `${this.BITCOIN_API_URL}/bitcoin`;

  ///QR
  QR_API_URL = environment.qrApiUrl;
  QR_URL = `${this.QR_API_URL}/qr`;

  //PAYMENT METHOD
  PAYMENT_METHOD_API_URL = environment.pspApiUrl;
  PAYMENT_METHOD_URL = `${this.PAYMENT_METHOD_API_URL}/payment-method`;
  SUBSCRIBED_PAYMENT_METHOD_URL = `${this.PAYMENT_METHOD_URL}/subscribed`;
  UPDATE_SUBSCRIBED_PAYMENT_METHOD_URL = `${this.PAYMENT_METHOD_URL}/subscribed/update`;
}
