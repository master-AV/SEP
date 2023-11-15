import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {AuthModule} from "../auth/auth.module";
import {RootComponent} from "./pages/root/root.component";
import {NotFoundComponent} from "./pages/not-found/not-found.component";

@NgModule({
  declarations: [
    AppComponent,
    RootComponent,
    NotFoundComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AuthModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
