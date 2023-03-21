import { Injectable } from '@angular/core';
import { User } from '../model/user';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(
    private httpClient: HttpClient,
    private router : Router,
    private route: ActivatedRoute,
    private cookieService: CookieService) { }

  authenticate(user: User) {
       return this.httpClient.post<User>('http://localhost:8080/login', user, { observe: 'response' })
            .pipe(tap(response => {
                console.log(response);
                const token = response.headers.get('Authorization').split(' ')[1];
                const userData = response.body;

                this.cookieService.set('token', token);
                this.cookieService.set('userId', userData.id.toString());
                this.cookieService.set('userRole', userData.role);


            })
        );

  }

  isUserLoggedIn() {
    const cookieExists: boolean = this.cookieService.check('token');
    return cookieExists;
  }

    logOut() {
      this.cookieService.delete('token');
      this.cookieService.delete('userId');
      this.cookieService.delete('userRole');
      this.router.navigate(['/login']);
    }
}
