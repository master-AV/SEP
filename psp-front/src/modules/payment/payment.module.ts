import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { MaterialModule } from "../material/material.module";
import { PaymentRoutes } from "./payment.routes";
import { ChoosePaymentMethodComponent } from './pages/choose-payment-method/choose-payment-method.component';
import { PaymentMethodComponent } from './components/payment-method/payment-method.component';
import { PaySuccessComponent } from './pages/payment-state/pay-success.component';

@NgModule({
  declarations: [
    ChoosePaymentMethodComponent,
    PaymentMethodComponent,
    PaySuccessComponent,
  ],
  imports: [
    CommonModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forChild(PaymentRoutes)
  ]
})
export class PaymentModule { }
