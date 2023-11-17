import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { MaterialModule } from "../material/material.module";
import { OffersRoutes } from "./offers.routes";
import { OfferComponent } from './components/offer/offer.component';
import { OffersComponent } from './pages/offers/offers.component';
import { SharedModule } from "../shared/shared.module";

@NgModule({
  declarations: [
    OfferComponent,
    OffersComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    RouterModule.forChild(OffersRoutes)
  ]
})
export class OffersModule { }
