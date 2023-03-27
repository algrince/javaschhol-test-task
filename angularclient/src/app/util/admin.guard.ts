import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, CanActivateChild } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { ActivatedRoute } from '@angular/router';

@Injectable({ providedIn: 'root' })
export class AdminGuard implements CanActivate {

    userRole: string;
    roleExist: boolean;

  constructor(
    private cookieService: CookieService,
    private route: ActivatedRoute,
    private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    this.userRole = this.cookieService.get("userRole");
    this.roleExist = this.cookieService.check("userRole")
    if (this.roleExist && this.userRole === 'BUYER' || !this.roleExist) {
        this.router.navigate(['/unauthorized']);
        return false;
    }
    return true
  }
}