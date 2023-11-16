import { Component } from '@angular/core';

@Component({
  selector: 'app-choose-payment-method',
  templateUrl: './choose-payment-method.component.html',
  styleUrls: ['./choose-payment-method.component.scss']
})
export class ChoosePaymentMethodComponent {

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
}
