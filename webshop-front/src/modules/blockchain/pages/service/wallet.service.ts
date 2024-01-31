import { Injectable } from '@angular/core';
import {WalletDto} from "../model/wallet-dto";
import {HttpClient} from "@angular/common/http";
import {ConfigService} from "../../../shared/service/config-service/config.service";
import {PaymentRequest} from "../../../payment/model/payment-request";

@Injectable({
  providedIn: 'root'
})
export class WalletService {

  constructor(private http: HttpClient, private configService: ConfigService) {}

  createWallet(wallet: WalletDto) {
      return this.http.post<any>(this.configService.PSP_WALLET_URL, wallet);
  }
}
