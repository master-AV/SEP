import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Offer } from 'src/model/offer';

@Component({
  selector: 'app-offer',
  templateUrl: './offer.component.html',
  styleUrls: ['./offer.component.scss']
})
export class OfferComponent {
  @Input() offer: Offer;
  @Input() index: number;

  constructor(private router: Router){}

  clickOnBuy(id: number){
    this.router.navigate([`/psp/payment/id=${id}`]);
    // window.location.href = `http://localhost:4201/psp/payment?id=${id}`;
  }
}
