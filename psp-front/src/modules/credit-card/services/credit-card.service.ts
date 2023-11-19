import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {CardDto} from "../model/card-dto";

@Injectable({
  providedIn: 'root'
})
export class CreditCardService {

  private readonly API_PATH = environment.ccUrl + '/cc'

  constructor (private httpClient: HttpClient) { }

  // toPaymentMethod(id: number): Observable<any> {
  //   var uri: string = `${this.API_PATH}/pay/` + id
  //   console.log(uri);
  //   return this.httpClient.get<any>(uri)
  // }

  sendInformation(cc: CardDto) {
    var uri: string = `${this.API_PATH}/pay`
    console.log(uri);
    console.log(cc);
    return this.httpClient.post<any>(uri, cc,  { observe: 'response' ,
      withCredentials: true,
      headers: {
        'Origin': 'http://localhost:4201' // Your front-end URL})
      }
    })

  }
}
