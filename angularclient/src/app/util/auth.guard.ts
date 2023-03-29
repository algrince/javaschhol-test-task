import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, CanActivateChild } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { ActivatedRoute } from '@angular/router';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {

    userRole: string;
    roleExist: boolean;
    userId: number;
    cookieId: string;
    isSameId: boolean;

  constructor(
    private cookieService: CookieService,
    private route: ActivatedRoute,
    private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    this.userId = this.route.snapshot.params['id'];
    console.log(this.userId);
    this.userRole = this.cookieService.get("userRole");
    this.cookieId = this.cookieService.get("userId");
    this.roleExist = this.cookieService.check("userRole")

    if (!this.roleExist) {
        this.router.navigate(['/login']);
        return false;
    } else if (this.userRole === 'BUYER' && this.userId.toString() !== this.cookieId ) {
        this.router.navigate(['/unauthorized']);
        return false;
    }
    return true
  }
}