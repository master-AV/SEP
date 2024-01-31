import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {CardDto} from "../model/card-dto";
import { ConfigService } from 'src/modules/shared/service/config-service/config.service';

@Injectable({
  providedIn: 'root'
})
export class CreditCardService {



  constructor (private httpClient: HttpClient, private configService: ConfigService) { }

  // toPaymentMethod(id: number): Observable<any> {
  //   var uri: string = `${this.API_PATH}/pay/` + id
  //   console.log(uri);
  //   return this.httpClient.get<any>(uri)
  // }

  sendInformation(cc: CardDto) {
    // return this.httpClient.post<any>(uri, cc,  { observe: 'response' ,
    //   withCredentials: true,
    //   headers: {
    //     'Origin': 'http://localhost:4201' // Your front-end URL})
    //   }
    // })
    return this.httpClient.post<any>(this.configService.CC_PAY_URL, cc, {observe: 'response'
      });
  }
}
