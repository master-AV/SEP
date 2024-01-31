import { Component } from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";
import { PaymentRequest } from '../../model/payment-request';
import { ToastrService } from 'ngx-toastr';
import { PaymentMethodService } from '../../services/payment-method-service/payment-method-service.service';
import { PaymentService } from '../../services/payment-service/payment.service';
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
    this.route.params.subscribe(params => {
      this.id = +params['id'];
      this.checked = params['checked'];
    });

    this.paymentMethodService.getSubscribedPaymentMethods().subscribe(response => {
      this.paymentMethods = response;
    });
  }

  constructor(private readonly fb: FormBuilder, private route: ActivatedRoute,
              private paymentService: PaymentService,
              private toast: ToastrService,
              private paymentMethodService: PaymentMethodService
              ) {

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
    this.paymentService.payment(paymentRequest).subscribe(response => {
      console.log(response);
      if (methodName == "CREDIT CARD")
            window.location.href = response.headers.get('Location')
      else if (methodName == "QR CODE")
        this.qrCodePayment(response, paymentRequest.userId)
      window.location.href = response.body.redirectUrl;
    },
      error => {
              this.toast.error(
              error.error,
              'Payment creation failed!'
            );
      }
    );

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

  private qrCodePayment(response, userId) {
    localStorage.setItem('qrCode', response['body']['qrCode']);
    localStorage.setItem('userId', String(userId));
    window.location.href = response.headers.get('Location')
    // this.qrService.toPaymentMethod(this.id).subscribe(response => {
    //   console.log(response);
    // });
  }

}
