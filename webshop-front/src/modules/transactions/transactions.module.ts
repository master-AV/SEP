import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AllTransactionsComponent } from './pages/all-transactions/all-transactions.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { MaterialModule } from '../material/material.module';
import { TransactionsRoutes } from './transactions.routes';
import { TransactionsTableComponent } from './components/transactions-table/transactions-table.component';

@NgModule({
  declarations: [
    AllTransactionsComponent,
    TransactionsTableComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forChild(TransactionsRoutes)
  ]
})
export class TransactionsModule { }
