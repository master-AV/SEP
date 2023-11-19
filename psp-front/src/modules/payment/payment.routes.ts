import {Routes} from "@angular/router";
import {ChoosePaymentMethodComponent} from "./pages/choose-payment-method/choose-payment-method.component";
import {PaySuccessComponent} from "./pages/payment-state/pay-success.component";

export const PaymentRoutes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    component: ChoosePaymentMethodComponent
  },
  {
    path: ':state',
    pathMatch: 'full',
    component: PaySuccessComponent
  },
  // {
  //   path: 'cc',
  //   pathMatch: 'full',
  //   component: AddCcParamComponent
  // }
]
