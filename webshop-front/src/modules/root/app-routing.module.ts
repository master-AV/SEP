import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RootLayoutComponent } from './pages/root-layout/root-layout.component';

const routes: Routes = [
  {
    path: "",
    component: RootLayoutComponent,
    children: [
      {
        path: "offers",
        loadChildren: () =>
          import("./../offers/offers.module").then((m) => m.OffersModule),
      },
      {
        path: "auth",
        loadChildren: () =>
          import("./../auth/auth.module").then((m) => m.AuthModule),
      },
      {
        path: "psp",
        loadChildren: () =>
          import("./../payment/payment.module").then((m) => m.PaymentModule),
      },
      {
        path: "cc",
        loadChildren: () =>
          import("./../credit-card/credit-card.module").then((m) => m.CreditCardModule),
      },
    ]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
