import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LogsTableComponent } from './pages/logs-table/logs-table.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { MaterialModule } from '../material/material.module';
import { LogsRoutes } from './logs.routes';

@NgModule({
  declarations: [
    LogsTableComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forChild(LogsRoutes)
  ]
})
export class LogsModule { }
