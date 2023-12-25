import { Component, OnInit } from '@angular/core';
import { OfferService } from '../../services/offer-service/offer.service';
import { Offer } from 'src/model/offer';

@Component({
  selector: 'app-offers',
  templateUrl: './offers.component.html',
  styleUrls: ['./offers.component.scss']
})
export class OffersComponent implements OnInit{
  constructor(private offerService: OfferService){}
  offers: Offer[] = [];

  ngOnInit() {
    console.log('e');
    this.offerService.getOffers().subscribe(
      response => {
        console.log(response);
        this.offers = response;
      }
    )
  }
}
