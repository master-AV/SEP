import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RootLayoutComponent } from './pages/root-layout/root-layout.component';

const routes: Routes = [
  {
    path: "psp",
    component: RootLayoutComponent,
    children: [
      {
        path: "payment",
        loadChildren: () =>
          import("./../payment/payment.module").then((m) => m.PaymentModule),
      },
    ]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
