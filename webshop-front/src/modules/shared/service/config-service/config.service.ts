import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {

  constructor() { }

  API_URL = environment.apiUrl;
  ///OFFERS
  OFFERS_URL = `${this.API_URL}/offers`;
  AUTH_URL = `${this.API_URL}/id`;
  LOGIN_URL = `${this.AUTH_URL}/login`;
  LOGOUT_URL = `${this.AUTH_URL}/logout`;
  CONFIRM_PIN_URL = `${this.AUTH_URL}/confirm-pin`;// must? ///////////////////////////////

  VERIFY_URL = `${this.API_URL}/verify`;///////////////////////////////
  SEND_CODE_AGAIN_URL = `${this.VERIFY_URL}/send-code-again`;///////////////////////////////
  USERS_URL = `${this.API_URL}/users`;
  CREATE_REGULAR_USER_URL = `${this.USERS_URL}/register`;
  ACTIVATE_ACCOUNT_URL = `${this.USERS_URL}/activate-account`;///////////////////////////////

  getLoginUrl(): string {
    return this.LOGIN_URL;
  }

  getLogoutUrl(): string {
    return this.LOGOUT_URL;
  }

  getGeneratePinUrl(email: string){
    return `${this.AUTH_URL}/generate-pin/${email}`;
  }

  getIncrementFailedAttempts(email: string){
    return `${this.AUTH_URL}/increment-failed-attempts/${email}`;
  }


  ///PAYPAL
  PAYPAL_URL = `${this.API_URL}/paypal`;

  ///BITCOIN
  BITCOIN_URL = `${this.API_URL}/bitcoin`;

  ///QR
  QR_URL = `${this.API_URL}/qr`;

  ///CC
  CC_URL = `${this.API_URL}/cc`;
  //PAY
  CC_PAY_URL = `${this.API_URL}/cc/pay`;

  //PSP
  PSP_URL = `${this.API_URL}/payment`;
  PSP_WALLET_URL = `${this.API_URL}/payment/add-wallet`;

  getPaymentIdUrl(id: number){
    return `${this.CC_URL}/payment/${id}`;
  }

    //PAYMENT METHOD
    PAYMENT_METHOD_URL = `${this.API_URL}/payment-method`;
    SUBSCRIBED_PAYMENT_METHOD_URL = `${this.PAYMENT_METHOD_URL}/subscribed`;
    UPDATE_SUBSCRIBED_PAYMENT_METHOD_URL = `${this.PAYMENT_METHOD_URL}/subscribed/update`;

}
