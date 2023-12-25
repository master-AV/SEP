import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { User } from 'src/modules/auth/model/user';
import { AuthService } from 'src/modules/auth/service/auth/auth.service';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  loggedUser:User;
  authSubscription: Subscription;

  constructor(private router: Router, private authService: AuthService) { }


  ngOnInit(): void {
    this.authSubscription = this.authService
    .getSubjectCurrentUser()
    .subscribe(user => {
      this.loggedUser = user;
    });
  }

  showScreen() {
    if(this.loggedUser.expiresMembership === null){
      return "BUY";
    } else if(this.loggedUser.expiresMembership < new Date()){
      return "EXTEND";
    } else {
      return "OK";
    }
  }

  payFee(){
    this.router.navigate(["/psp/payment/id=0"]);
  }

}
