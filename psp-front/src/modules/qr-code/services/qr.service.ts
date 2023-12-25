import { Injectable } from '@angular/core';
import {CardDto} from "../../credit-card/model/card-dto";
import {environment} from "../../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class QrService {

  // private readonly BANK_PATH = environment.bankApiUrl;
  private readonly API_PATH = environment.apiUrl + ''

  constructor (private httpClient: HttpClient
  ) { }

  // getQRCode() : Observable<any>{
  //   var uri: string = `${this.BANK_PATH}` + '/enc/qr'
  //   const requestOptions: Object = {
  //     responseType: 'text',
  //   };
  //   return this.httpClient.get<any>(uri, requestOptions
  //   )
  // }

  payWithQRCode(qrCode: string, qrId: number) : Observable<any>{
    var uri: string = `${this.API_PATH}/cc/qr/pay/${localStorage.getItem("userId")}/${qrId}`
    return this.httpClient.post<any>(uri, qrCode,  { observe: 'response' ,
      withCredentials: true,
      headers: {
        'Access-Control-Allow-Origin': 'http://localhost:4201' // Your front-end URL})
      }
    })
  }

  saveCardDto(cc: CardDto) {
    return this.httpClient.post<any>(`${this.API_PATH}/cc/qr/save/${localStorage.getItem("userId")}`, cc)
  }
}
