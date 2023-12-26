import { Component } from '@angular/core';

import { FormBuilder, FormGroup } from '@angular/forms';
import { PaymentMethod, UpdatePaymentMethod } from '../../model/payment-method';
import { PaymentMethodService } from '../../services/payment-method-service/payment-method-service.service';

@Component({
  selector: 'app-payment-method-subscribe',
  templateUrl: './payment-method-subscribe.component.html',
  styleUrls: ['./payment-method-subscribe.component.scss']
})
export class PaymentMethodSubscribeComponent {
  paymentMethods: PaymentMethod[] = [];
  checkboxForm: FormGroup;

  constructor(private paymentMethodService: PaymentMethodService, private fb: FormBuilder){
    const formGroupConfig = {};
    this.paymentMethodService.getAllPaymentMethods().subscribe(response => {
      this.paymentMethods = response;
      response.forEach(method => {
        formGroupConfig[method.name] = method.subscribed;
      });
      this.checkboxForm = this.fb.group(formGroupConfig);
    });
  }

  onSubmit() {
    let updatePaymentMethods: UpdatePaymentMethod[] = [];
    this.paymentMethods.forEach(
      (paymentMethod) => {
        updatePaymentMethods.push(
          {
            name: paymentMethod.name, 
            subscribed: this.checkboxForm?.get(paymentMethod.name)?.value
          }
        );
      }
    );
     this.paymentMethodService.updatePaymentMethods(updatePaymentMethods).subscribe(
      response => {
        console.log(response);
      }
     )

    this.checkboxForm.markAsPristine();
  }
 
  haveCheckedBoxes() {
    const found = this.paymentMethods.find(item => this.checkboxForm.get(item.name).value === true);
    
    return found !== undefined;
  }

}
