import { Component, Input, OnInit } from '@angular/core';
import { Transaction } from '../../model/transaction';

@Component({
  selector: 'app-transactions-table',
  templateUrl: './transactions-table.component.html',
  styleUrls: ['./transactions-table.component.scss']
})
export class TransactionsTableComponent implements OnInit {
  @Input() dataSource: Transaction[];
  displayedColumns: string[] = ['position', 'date', 'user', 'offer', 'payment_method', 'price'];
  constructor() { }

  ngOnInit(): void {
  }

}
