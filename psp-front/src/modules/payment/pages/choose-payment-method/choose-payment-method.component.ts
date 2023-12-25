import { Component } from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";
import {CreditCardService} from "../../services/credit-card/credit-card.service";
import { PaymentRequest } from '../../model/payment-request';
import { PaypalService } from '../../services/paypal-service/paypal.service';
import { BitcoinService } from '../../services/bitcoin-service/bitcoin.service';
import { ToastrService } from 'ngx-toastr';
import {QrCodeService} from "../../services/qr/qr-code.service";
import { PaymentMethodService } from '../../services/payment-method-service/payment-method-service.service';
@Component({
  selector: 'app-choose-credit-card',
  templateUrl: './choose-payment-method.component.html',
  styleUrls: ['./choose-payment-method.component.scss']
})
export class ChoosePaymentMethodComponent {
  id: number;

  paymentMethods = [];

  ngOnInit() {
    this.route.queryParamMap.subscribe(params => {
      this.id = +params.get('id');
    });

    this.paymentMethodService.getSubscribedPaymentMethods().subscribe(response => {
      this.paymentMethods = response;
    });
  }

  constructor(private readonly fb: FormBuilder, private route: ActivatedRoute,
              private cardService: CreditCardService, private paypalService: PaypalService,
              private bitcoinService: BitcoinService, private toast: ToastrService,
              private qrService: QrCodeService, private paymentMethodService: PaymentMethodService
              ) {

  }

  creditCardPayment() {
    this.cardService.toPaymentMethod(this.id).subscribe(response => {
      window.location.href = response.headers.get('Location')
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
        break;
      case "CREDIT CARD":
        this.creditCardPayment();
        break;
      case "QR CODE":
        this.qrCodePayment(paymentRequest);
        break;
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

  private qrCodePayment(paymentRequest: PaymentRequest) {
    this.qrService.toPaymentMethod(this.id).subscribe(response => {
      console.log(response);
      localStorage.setItem('qrCode', response['body']['qrCode']);
      localStorage.setItem('userId', String(paymentRequest.userId));
      window.location.href = response.headers.get('Location')
    });
  }

}
