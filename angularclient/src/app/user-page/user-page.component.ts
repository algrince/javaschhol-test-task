import { Component, OnInit } from '@angular/core';
import { User} from '../model/user';
import { UserService } from '../service/user.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css']
})
export class UserPageComponent implements OnInit {

  user: User;
  id: number;

  constructor(
    private userService : UserService,
    private router : Router,
    private route: ActivatedRoute) {
      this.user = new User();
  }


  ngOnInit() {
    this.id = this.route.snapshot.params['id'];

    this.userService.findOne(this.id).subscribe(data => {
      this.user = data;
    });
  }
}
