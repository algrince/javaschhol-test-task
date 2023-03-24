import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler } from '@angular/common/http';
import { AuthenticationService } from './authentication.service';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class BasicAuthHttpInterceptorService implements HttpInterceptor {

  constructor(private cookieService: CookieService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler) {

        if (this.cookieService.check('token')) {

            req = req.clone({
                setHeaders: {
                    Authorization: "Bearer " + this.cookieService.get('token')
                }
            })
        }

        return next.handle(req);
  }
}
