import { Injectable } from '@angular/core';
import { User } from '../model/user';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';


export class JwtResponse {
  constructor(
    public jwtToken: string,
     ) {}

}

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private httpClient: HttpClient) { }

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
      sessionStorage.removeItem('token')
    }
}
