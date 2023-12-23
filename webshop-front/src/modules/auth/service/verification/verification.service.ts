import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfigService } from 'src/modules/shared/service/config-service/config.service';
import { VerifyRequest } from '../../model/registration_and_verification/verify-request';
import {User} from "../../model/user";

@Injectable({
  providedIn: 'root'
})
export class VerificationService {

  constructor(
    private http: HttpClient,
    private configService: ConfigService
  ) {}

  sendCodeAgain(verifyId: string): Observable<User> {
    return this.http.post<User>(
      this.configService.SEND_CODE_AGAIN_URL,
      verifyId
    );
  }

  createVerifyRequest(verifyId: string, securityCode: number): VerifyRequest {
    return {
      verifyId: verifyId,
      securityCode: securityCode,
    };
  }

}
