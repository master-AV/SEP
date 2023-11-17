import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Offer } from 'src/model/offer';
import { ConfigService } from 'src/modules/shared/service/config-service/config.service';

@Injectable({
  providedIn: 'root'
})
export class OfferService {

  constructor(private http: HttpClient, private configService: ConfigService) {}

  getOffers() {
    console.log('offers');
    return this.http.get<Offer[]>(this.configService.OFFERS_URL);
  }
}
