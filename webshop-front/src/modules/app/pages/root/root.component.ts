// import { Component } from '@angular/core';
// import {Router} from "@angular/router";

import {Component, SimpleChanges} from "@angular/core";
import {Router} from "@angular/router";
@Component({
  selector: 'app-root',
  templateUrl: './root.component.html',
  styleUrls: ['./root.component.scss']
})
export class RootComponent {

  public pageTitle: string= '';
  public signedIn!: boolean;
  public update: boolean = false;
  private userId!: number;

  constructor (){

  }

  ngOnInit () : void {
  }

  ngOnChanges(changes: SimpleChanges) {
  }

}
