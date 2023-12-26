import { Component } from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";
import {CreditCardService} from "../../services/credit-card/credit-card.service";
import { PaymentService } from '../../services/payment-service/payment.service';
import { BitcoinService } from '../../services/bitcoin-service/bitcoin.service';
import { ToastrService } from 'ngx-toastr';
import { PaymentRequest } from '../../model/payment-request';
import {QrCodeService} from "../../services/qr/qr-code.service";
@Component({
  selector: 'app-choose-credit-card',
  templateUrl: './choose-payment-method.component.html',
  styleUrls: ['./choose-payment-method.component.scss']
})
export class ChoosePaymentMethodComponent {
  id: number;
  checked: boolean;

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
    this.route.params.subscribe(params => {
      this.id = +params['id'];
      this.checked = params['checked'];
      // Use the retrieved parameter value
    });
  }

  constructor(private readonly fb: FormBuilder, private route: ActivatedRoute,
              private paymentService: PaymentService, private toast: ToastrService) {

  }

  // creditCardPayment() {
  //   this.cardService.toPaymentMethod(this.id).subscribe(response => {
  //     window.location.href = response.headers.get('Location')

  //   });
  // }

  clickOnPayment(methodName: string){
    const paymentRequest: PaymentRequest = {
      userId: 1,
      offerId: this.id,
      method: methodName,
      subscribedMembership: this.checked
    };
    this.paymentService.payment(paymentRequest).subscribe(
      response => {
        console.log(response);
      }
    )
  }

  // paypalPayment(paymentRequest: PaymentRequest) {

  //   this.paypalService.paypalPayment(paymentRequest).subscribe(
  //     response => {
  //         window.location.href = response.redirectUrl;
  //       },
  //     error => {
  //       this.toast.error(
  //         error.error,
  //         'Payment creation failed!'
  //       );
  //     }
  //   )
  // }

  // bitcoinPayment(paymentRequest: PaymentRequest) {

  //   this.bitcoinService.bitcoinPayment(paymentRequest).subscribe(
  //     response => {
  //       this.toast.info(response.message);
  //     }
  //   )
  // }

  // private qrCodePayment() {
  //   this.qrService.qrCodePayment().subscribe(
  //     response => {
  //       this.toast.info(response.message);
  //     }
  //   )
  // }
}
