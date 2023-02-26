import { Component, OnInit } from '@angular/core';

import { UserService } from '../service/user.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-user-delete',
  templateUrl: './user-delete.component.html',
  styleUrls: ['./user-delete.component.css']
})
export class UserDeleteComponent implements OnInit {

    userId: number;

    constructor(
        private userService : UserService,
        private router : Router,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.userId = this.route.snapshot.params['id'];
    }

    onSubmit() {
        this.userService.delete(this.userId).subscribe(result => this.gotoHomepage());
    }

    gotoHomepage() {
        this.router.navigate(['/home']);
    }

}
