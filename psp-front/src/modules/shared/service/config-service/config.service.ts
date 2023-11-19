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

}