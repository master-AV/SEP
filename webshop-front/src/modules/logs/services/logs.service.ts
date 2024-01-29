import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from 'src/modules/shared/service/config-service/config.service';
import { Observable } from 'rxjs';
import { Log } from '../model/log';

@Injectable({
  providedIn: 'root'
})
export class LogsService {

  constructor(private httpClient: HttpClient, private configService: ConfigService) {}

  getAll(): Observable<Log[]> {
    return this.httpClient.get<Log[]>(this.configService.LOG_URL);
  }

}
