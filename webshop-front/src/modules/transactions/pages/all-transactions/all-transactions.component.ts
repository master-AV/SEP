import { Component, OnInit } from '@angular/core';
import { Transaction } from '../../model/transaction';
import { TransactionsService } from '../../service/transaction-service/transactions.service';
import { UserService } from 'src/modules/auth/service/user-service/user.service';
import { User } from 'src/modules/auth/model/user';

@Component({
  selector: 'app-all-transactions',
  templateUrl: './all-transactions.component.html',
  styleUrls: ['./all-transactions.component.scss']
})
export class AllTransactionsComponent implements OnInit {
  constructor(private transactionService: TransactionsService, private userService: UserService) { }
  allTransactions: Transaction[] = [];
  transactionsForSelectedUser: Transaction[] = [];
  users: User[] = [];
  selectedUser = '';
  ngOnInit(): void {
    this.transactionService.getAll().subscribe(
      response => {
        this.allTransactions = response;
      }
    );

    this.userService.getAll().subscribe(
      response => {
        this.users = response;
        this.selectedUser = response[0].id.toString();
      }
    )
  }

  changeSelectedUser() {
    this.transactionService.getById(+this.selectedUser).subscribe(
      response => {
        this.transactionsForSelectedUser = response
      }
    )
  }

}
