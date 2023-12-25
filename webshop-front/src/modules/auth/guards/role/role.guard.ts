import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { AuthService } from '../../service/auth/auth.service';
import {User} from "../../model/user";

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {
  constructor(public authService: AuthService, public  router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {

    const expectedRoles: string = route.data['expectedRoles'];
    const user: User = this.authService.getLoggedParsedUser();
    if (!user){
      this.router.navigate(["/auth/login"]);
      return false;
    }

    const roles: string[] = expectedRoles.split("|", 3);
    if (roles.indexOf(user.role.roleName) === -1){
      this.router.navigate(["/auth/login"]);
      return false;
    }

    return true;

  }

}
