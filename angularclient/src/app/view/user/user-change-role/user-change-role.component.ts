import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { User } from '../../../model/user';
import { UserService } from '../../../service/user.service';


@Component({
  selector: 'app-user-change-role',
  templateUrl: './user-change-role.component.html',
  styleUrls: ['./user-change-role.component.css']
})

export class UserChangeRoleComponent implements OnInit {

    user: User;
    userId: number;
    buyer: string = "BUYER";
    employee: string = "EMPLOYEE";
    admin: string = "ADMIN";

    constructor(
        private userService: UserService,
        private router: Router,
        private route: ActivatedRoute) {
            this.user = new User();
        }

    ngOnInit() {
        this.userId = this.route.snapshot.params['id']

        this.userService.findOneUser(this.userId)
            .subscribe(data => {this.user = data});
    }

    onSubmit() {
        this.userService.updateRole(this.userId, this.user)
            .subscribe(result => this.gotoUserList());
    }

    gotoUserList() {
        this.router.navigate(['/admin/users']);
    }
}
