import { Injectable } from '@angular/core';
import {environment} from "../../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CreditCardService {

  private readonly API_PATH = environment.apiUrl + '/cc'

  constructor (private httpClient: HttpClient) { }

  toPaymentMethod(id: number): Observable<any> {
    var uri: string = `${this.API_PATH}/payment/` + id
    console.log(uri);
    return this.httpClient.get<any>(uri,  { observe: 'response' ,
      withCredentials: true,
      headers: {
        'Origin': 'http://localhost:4201' // Your front-end URL})
      }
  });
  }
}
