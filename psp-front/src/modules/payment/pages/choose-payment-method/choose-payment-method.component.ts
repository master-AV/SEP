import { Component } from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";
import {CreditCardService} from "../../services/credit-card/credit-card.service";

@Component({
  selector: 'app-choose-credit-card',
  templateUrl: './choose-payment-method.component.html',
  styleUrls: ['./choose-payment-method.component.scss']
})
export class ChoosePaymentMethodComponent {
  id: number;

  paymentMethods = [
    {
      name: 'CREDIT CARD',
      img: './assets/credit-card.png'
    },
    {
      name: 'QR CODE',
      img: './assets/qr-code.png'
    },
    {
      name: 'PAYPAL',
      img: './assets/paypal.png'
    },
    {
      name: 'BITCOIN',
      img: './assets/bitcoin.png'
    }
  ];

  ngOnInit() {
    // Accessing URL parameter using ActivatedRoute
    this.route.queryParamMap.subscribe(params => {
      this.id = +params.get('id');
      console.log('ID:', this.id); // Use the retrieved parameter value
    });
  }

  constructor(private readonly fb: FormBuilder,
              private route: ActivatedRoute,
              private cardService: CreditCardService) {

  }

  clickOnPayment(methodName: string){

    switch(methodName){
      case "CREDIT CARD":
        this.creditCardPayment();
        break;
    }
  }


  creditCardPayment() {
    this.cardService.toPaymentMethod(this.id).subscribe(response =>{
      console.log(response)
      console.log("********************************************")
      // console.log(response.headers)
      // console.log(response.headers.get('Location'))
      window.location.href = response.headers.get('Location')

    });
  }
}
