import {Routes} from "@angular/router";
import {QrPaymentComponent} from "./pages/qr-payment/qr-payment.component";
import {SaveCcInformationComponent} from "./pages/save-cc-information/save-cc-information.component";

export const QRCodeRoutes: Routes = [
  {
    path: ':id',
    pathMatch: 'full',
    component: QrPaymentComponent
  },
  {
    path: 'save/:id',
    pathMatch: 'full',
    component: SaveCcInformationComponent
  },
]
