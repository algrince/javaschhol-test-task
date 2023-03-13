import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from '../../../service/authentication.service';
import { User } from '../../../model/user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  user: User;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private authenticationService: AuthenticationService) {

  this.user = new User();
  }

  onSubmit() {
    this.authenticationService.authenticate(this.user)
        .subscribe(result => this.gotoHomepage());
  }

  gotoHomepage() {
    this.router.navigate(['/home']);
  }

}
