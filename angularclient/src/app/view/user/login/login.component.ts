import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from '../../../service/authentication.service';
import { CookieService } from 'ngx-cookie-service';
import { User } from '../../../model/user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  user: User;
  errorMessage: string;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private authenticationService: AuthenticationService,
    private cookieService: CookieService) {

  this.user = new User();
  }

  onSubmit() {
    this.authenticationService.authenticate(this.user)
        .subscribe(() => {
              this.gotoHomepage();
          },
          (errorResponse) => {
              this.errorMessage = errorResponse.error.message;
          }
       );
  }

  gotoHomepage() {
    this.router.navigate(['/home']);
  }

}
