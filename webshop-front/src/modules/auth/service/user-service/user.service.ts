import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { VerifyRequest } from 'src/modules/auth/model/registration_and_verification/verify-request';
import { User } from '../../model/user';
import { ConfigService } from '../../../shared/service/config-service/config.service';
import { UserRegistrationRequest } from '../../model/registration_and_verification/regular-user-registration';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, private configService: ConfigService) { }


  registerUser(newUser: UserRegistrationRequest): Observable<User> {
    return this.http.post<User>(
      this.configService.CREATE_REGULAR_USER_URL,
      newUser
    );
  }

  verify(verifyRequest: VerifyRequest): Observable<boolean> {
    return this.http.put<boolean>(this.configService.ACTIVATE_ACCOUNT_URL, verifyRequest);
  }

}
