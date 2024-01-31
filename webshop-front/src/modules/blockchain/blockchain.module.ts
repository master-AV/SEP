import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddWalletComponent } from './pages/add-wallet/add-wallet.component';
import {RouterModule} from "@angular/router";
import {BlockchainRoutes} from "./blockchain.routes";
import {MatFormFieldModule} from "@angular/material/form-field";
import {ReactiveFormsModule} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";



@NgModule({
  declarations: [
    AddWalletComponent,
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(BlockchainRoutes),
    MatFormFieldModule,
    ReactiveFormsModule,
    MatInputModule
  ]
})
export class BlockchainModule { }
