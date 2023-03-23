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
  private signupUrl: string;

  constructor(private http: HttpClient) {
      this.usersUrl = 'http://localhost:8080/users';
      this.loginUrl = 'http://localhost:8080/login';
      this.signupUrl = 'http://localhost:8080/registration';
  }

  public findAll(): Observable<User[]> {
    return this.http.get<User[]>(this.usersUrl);
  }

  public findOneUser(id: number): Observable<User> {
    const url = `${this.usersUrl}/${id}`;
    return this.http.get<User>(url);
  }

  public save(user: User) {
    return this.http.post<User>(this.signupUrl, user);
  }

  public update(id: number, user: User) {
    const updateUrl = `${this.usersUrl}/${id}`;
    return this.http.put<User>(updateUrl, user);
  }

  public delete(id: number) {
    const deleteUrl = `${this.usersUrl}/${id}`;
    return this.http.delete<User>(deleteUrl);
  }

  public login(user: User): Observable<object> {
    return this.http.post<User>(this.loginUrl, user);
  }
}
