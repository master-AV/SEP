import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule} from "@angular/router";
import {AuthRoutes} from "./auth.routes";



@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forChild(AuthRoutes),
  ]
})
export class AuthModule { }
