import { Injectable } from '@angular/core';
import { User } from '../model/user';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { map } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(
    private httpClient: HttpClient,
    private router : Router,
    private route: ActivatedRoute) { }

  authenticate(user: User) {
        return this.httpClient.post<User>('http://localhost:8080/login', user)
            .pipe(map(
                userData => {
                    let tokenStr = 'Bearer ' + userData;
                    sessionStorage.setItem('token', tokenStr);
                    return userData;
                    }
                )
            );

  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem('token');
    return !(user === null);
  }

    logOut() {
      sessionStorage.removeItem('token');
      this.router.navigate(['/login']);
    }
}
