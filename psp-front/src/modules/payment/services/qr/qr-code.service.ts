import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ConfigService} from "../../../shared/service/config-service/config.service";
import {PaymentRequest} from "../../model/payment-request";
import {Message} from "../../model/message";
import {Observable} from "rxjs";
import {environment} from "../../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class QrCodeService {
  private readonly API_PATH = environment.apiUrl + '/cc'

  constructor(private http: HttpClient, private configService: ConfigService) {}

  qrCodePayment() {
    return this.http.post<Message>(this.configService.QR_URL, "");
  }


  toPaymentMethod(id: number): Observable<any> {
    var uri: string = `${this.API_PATH}/qr/` + id
    console.log(uri);
    return this.http.get<any>(uri,  { observe: 'response' ,
      withCredentials: true,
      headers: {
        'Origin': 'http://localhost:4201' // Your front-end URL})
      }
    });
  }
}
