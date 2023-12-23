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
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
