import { Routes } from "@angular/router";
import { ChoosePaymentMethodComponent } from "./pages/choose-payment-method/choose-payment-method.component";

export const PaymentRoutes: Routes = [
    {
        path: '',
        pathMatch: 'full',
        component: ChoosePaymentMethodComponent
    }
]