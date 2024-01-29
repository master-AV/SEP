import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { QrPaymentComponent } from './pages/qr-payment/qr-payment.component';
import {RouterModule} from "@angular/router";
import {QRCodeRoutes} from "./qr-code.routes";
import {MatButtonModule} from "@angular/material/button";
import { SaveCcInformationComponent } from './pages/save-cc-information/save-cc-information.component';
import {SharedModule} from "../shared/shared.module";



@NgModule({
  declarations: [
    QrPaymentComponent,
    SaveCcInformationComponent
  ],
    imports: [
        CommonModule,
        RouterModule.forChild(QRCodeRoutes),
        MatButtonModule,
        SharedModule,
    ]
})
export class QrCodeModule { }
