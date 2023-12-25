import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { User } from 'src/modules/auth/model/user';
import { AuthService } from 'src/modules/auth/service/auth/auth.service';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.scss']
})
export class NavBarComponent implements OnInit {
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

  logOut(){
    this.authService.logOut().subscribe();
    sessionStorage.clear();
    this.router.navigate(['/auth/login'])
  }

}
