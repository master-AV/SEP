import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ConfigService} from "../../../shared/service/config-service/config.service";
import {PaymentRequest} from "../../model/payment-request";
import {Message} from "../../model/message";

@Injectable({
  providedIn: 'root'
})
export class QrCodeService {

  constructor(private http: HttpClient, private configService: ConfigService) {}

  qrCodePayment() {
    return this.http.post<Message>(this.configService.QR_URL, "");
  }
}
