import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddCcInformationComponent } from './pages/add-cc-information/add-cc-information.component';
import {RouterModule} from "@angular/router";
import {CreditCardRoutes} from "./credit-card.routes";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {ReactiveFormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    AddCcInformationComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(CreditCardRoutes),
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule
  ]
})
export class CreditCardModule { }
