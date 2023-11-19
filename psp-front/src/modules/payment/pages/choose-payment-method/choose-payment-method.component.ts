import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PaymentRequest } from '../../model/payment-request';
import { PaypalService } from '../../services/paypal-service/paypal.service';
import { BitcoinService } from '../../services/bitcoin-service/bitcoin.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-choose-payment-method',
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

  constructor(private route: ActivatedRoute, private paypalService: PaypalService, private bitcoinService: BitcoinService, private toast: ToastrService) {
    this.route.queryParams.subscribe(params => {
      this.id = params['id'];
    });
  }

  clickOnPayment(methodName: string){
    const paymentRequest: PaymentRequest = {
      userId: 1,
      offerId: this.id
    };

    switch(methodName){
      case "PAYPAL":
        this.paypalPayment(paymentRequest);   
        break;
      case "BITCOIN":
        this.bitcoinPayment(paymentRequest);
    }
  }

  paypalPayment(paymentRequest: PaymentRequest) {

    this.paypalService.paypalPayment(paymentRequest).subscribe(
      response => {
          window.location.href = response.redirectUrl;
        },
      error => {
        this.toast.error(
          error.error,
          'Payment creation failed!'
        );
      }
    )
  }

  bitcoinPayment(paymentRequest: PaymentRequest) {

    this.bitcoinService.bitcoinPayment(paymentRequest).subscribe(
      response => {
        this.toast.info(response.message);
      }
    )
  }
}
