import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../model/user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private usersUrl: string;
  private loginUrl: string;
  private oneUserUrl: string;

  constructor(private http: HttpClient) {
      this.usersUrl = 'http://localhost:8080/users';
      this.oneUserUrl = 'http://localhost:8080/users/{id}'
      this.loginUrl = 'http://localhost:8080/login';
  }

  public findAll(): Observable<User[]> {
    return this.http.get<User[]>(this.usersUrl);
  }

  public findOneUser(id: number): Observable<User> {
    return this.http.get<User>(this.oneUserUrl)
  }

  public save(user: User) {
    return this.http.post<User>(this.usersUrl, user);
  }

  public login(user: User): Observable<object> {
    return this.http.post<User>(this.loginUrl, user);
  }
}
