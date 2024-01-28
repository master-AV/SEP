import { Component } from '@angular/core';

import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { PaymentMethod, UpdatePaymentMethod } from '../../model/payment-method';
import { PaymentMethodService } from '../../services/payment-method-service/payment-method-service.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-payment-method-subscribe',
  templateUrl: './payment-method-subscribe.component.html',
  styleUrls: ['./payment-method-subscribe.component.scss']
})
export class PaymentMethodSubscribeComponent {
  paymentMethods: PaymentMethod[] = [];
  checkboxForm: FormGroup;

  constructor(private paymentMethodService: PaymentMethodService, private fb: FormBuilder, private toast: ToastrService){
  }

  onSubmit() {
    let updatePaymentMethods: UpdatePaymentMethod[] = [];
    console.log(updatePaymentMethods);
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
    console.log(updatePaymentMethods);
     this.paymentMethodService.updatePaymentMethods(updatePaymentMethods).subscribe(
      response => {
        this.toast.success('Successfully saved changes');
      }
     )

    this.checkboxForm.markAsPristine();
  }
 
  haveCheckedBoxes() {
    const found = this.paymentMethods.find(item => this.checkboxForm.get(item.name).value === true);
    
    return found !== undefined;
  }

  ngOnInit(){
    const formGroupConfig = {};
    this.paymentMethodService.getAllPaymentMethods().subscribe(response => {
      this.paymentMethods = response;
      response.forEach(method => {
        formGroupConfig[method.name] = new FormControl(method.subscribed);
      });
      this.checkboxForm = this.fb.group(formGroupConfig);
    });
  }

}
