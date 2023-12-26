import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { MaterialModule } from "../material/material.module";
import { PaymentMethodComponent } from './components/payment-method/payment-method.component';
import { SuccessPaypalComponent } from './pages/success-paypal/success-paypal.component';
import {PaySuccessComponent} from "./pages/payment-state/pay-success.component";
import { ChoosePaymentMethodComponent } from "./pages/choose-payment-method/choose-payment-method.component";
import { PaymentRoutes } from "./payment.routes";

@NgModule({
  declarations: [
    ChoosePaymentMethodComponent,
    PaymentMethodComponent,
    PaySuccessComponent,
    SuccessPaypalComponent
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
