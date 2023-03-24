import { Component, OnInit } from '@angular/core';
import { User } from '../../../model/user';
import { UserService } from '../../../service/user.service';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  users: User[];
  role: string;

  constructor(
    private userService: UserService,
    private cookieService: CookieService) {}

  ngOnInit() {
    this.userService.findAll()
        .subscribe(data => {this.users = data});

    this.role = this.cookieService.get('userRole');
  }

}
