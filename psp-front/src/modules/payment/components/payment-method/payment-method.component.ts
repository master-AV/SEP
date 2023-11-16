import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-payment-method',
  templateUrl: './payment-method.component.html',
  styleUrls: ['./payment-method.component.scss']
})
export class PaymentMethodComponent implements OnInit{
  
  @Input() nameOfMethod: string;
  @Input() imgOfMethod: string;
  
  ngOnInit(): void {
  
  }
}
