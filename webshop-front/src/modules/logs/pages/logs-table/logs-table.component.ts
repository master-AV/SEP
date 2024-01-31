import { Component, OnInit } from '@angular/core';
import { Log } from '../../model/log';
import { LogsService } from '../../services/logs.service';

@Component({
  selector: 'app-logs-table',
  templateUrl: './logs-table.component.html',
  styleUrls: ['./logs-table.component.scss']
})
export class LogsTableComponent implements OnInit {

  displayedColumns: string[] = ['dateTime', 'logLevel', 'logMessage'];
  logs: Log[] = [];
  constructor(private logService: LogsService) { }

  ngOnInit(): void {
    this.logService.getAll().subscribe(
      response => {
        this.logs = response;
      }
    )
  }

  formatDate(date: string){
    const newDate = new Date(date);
    const formattedDate = `${newDate.getDate().toString().padStart(2, '0')}.${(newDate.getMonth() + 1).toString().padStart(2, '0')}.${newDate.getFullYear()}.`;
    const formattedTime = `${newDate.getHours().toString().padStart(2, '0')}:${newDate.getMinutes().toString().padStart(2, '0')}:${newDate.getSeconds().toString().padStart(2, '0')}`;
    
    const formattedDateTime = `${formattedDate} ${formattedTime}`;
    return formattedDateTime;
  }

}
