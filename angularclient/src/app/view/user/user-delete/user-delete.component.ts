import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UserService } from '../../../service/user.service';
import { CookieService } from 'ngx-cookie-service';
import { AuthenticationService } from '../../../service/authentication.service';

@Component({
  selector: 'app-user-delete',
  templateUrl: './user-delete.component.html',
  styleUrls: ['./user-delete.component.css']
})
export class UserDeleteComponent implements OnInit {

    userId: number;
    role: string;

    constructor(
        private userService : UserService,
        private router : Router,
        private route: ActivatedRoute,
        private cookieService: CookieService,
        private authenticationService: AuthenticationService
    ) {}

    ngOnInit() {
        this.userId = this.route.snapshot.params['id'];
        this.role = this.cookieService.get('userRole');
    }

    onSubmit() {
        this.userService.delete(this.userId).subscribe(result => this.gotoHomepage());
    }

    gotoHomepage() {
        if (this.role === 'BUYER') {
            this.authenticationService.logOut();
            this.router.navigate(['/home']);
        } else {
            this.router.navigate(['/admin/users']);
        }

    }

}
