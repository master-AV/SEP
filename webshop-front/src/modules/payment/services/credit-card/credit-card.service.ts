import { Injectable } from '@angular/core';
import {environment} from "../../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import { ConfigService } from 'src/modules/shared/service/config-service/config.service';

@Injectable({
  providedIn: 'root'
})
export class CreditCardService {

  constructor (private httpClient: HttpClient, private configService: ConfigService) { }

  toPaymentMethod(id: number): Observable<any> {
  //   return this.httpClient.get<any>(uri,  { observe: 'response' ,
  //     withCredentials: true,
  //     headers: {
  //       'Origin': 'http://localhost:4201' // Your front-end URL})
  //     }
  // });
    return this.httpClient.get<any>(this.configService.getPaymentIdUrl(id));
  }
}
