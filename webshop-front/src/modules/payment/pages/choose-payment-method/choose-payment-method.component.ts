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

  paymentMethods = [];

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
      {
        //console.log(response.headers)
        //console.log(response.headers.get('Location'))
        window.location.href = response.headers.get('Location')
      }
      else if (methodName == "QR CODE")
        this.qrCodePayment(response, paymentRequest.userId)
      else{
        window.location.href = response.body.redirectUrl;
      }
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
