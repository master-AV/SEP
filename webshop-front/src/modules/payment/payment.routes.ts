import {Routes} from "@angular/router";
import {ChoosePaymentMethodComponent} from "./pages/choose-payment-method/choose-payment-method.component";
import {PaySuccessComponent} from "./pages/payment-state/pay-success.component";
import { PaymentMethodSubscribeComponent } from "./pages/payment-method-subscribe/payment-method-subscribe.component";
import { RoleGuard } from "../auth/guards/role/role.guard";

export const PaymentRoutes: Routes = [
  {
    path: 'payment/payment-method/:id/:checked',
    pathMatch: 'full',
    component: ChoosePaymentMethodComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: 'ROLE_USER' }
  },
  {
    path: ':state',
    pathMatch: 'full',
    component: PaySuccessComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: 'ROLE_USER' }
  },
  {
    path: 'payment-method/subscribed',
    pathMatch: 'full',
    component: PaymentMethodSubscribeComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: 'ROLE_ADMIN' }
  },
  // {
  //   path: 'cc',
  //   pathMatch: 'full',
  //   component: AddCcParamComponent
  // }
]
