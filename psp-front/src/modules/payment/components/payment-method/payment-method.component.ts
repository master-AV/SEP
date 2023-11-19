import { Component, Input, OnInit } from '@angular/core';
import {CreditCardService} from "../../services/credit-card/credit-card.service";

@Component({
  selector: 'app-payment-method',
  templateUrl: './payment-method.component.html',
  styleUrls: ['./payment-method.component.scss']
})
export class PaymentMethodComponent implements OnInit{

  @Input() nameOfMethod: string;
  @Input() imgOfMethod: string;

  constructor(private paymentMethodService: CreditCardService) {
  }

  ngOnInit(): void {

  }

  callPSP() {
    console.log("SHOULD CALL")

  }
}
