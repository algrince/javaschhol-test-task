import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Address } from '../../../model/address';
import { User } from '../../../model/user';
import { AddressService } from '../../../service/address.service';
import { CookieService } from 'ngx-cookie-service';


@Component({
  selector: 'app-address-form',
  templateUrl: './address-form.component.html',
  styleUrls: ['./address-form.component.css']
})
export class AddressFormComponent {

    address: Address;
    errors: string[];
    userId: number;
    user: User;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private addressService: AddressService,
        private cookieService: CookieService) {
            this.address = new Address();
            this.user = new User();
        }

    ngOnInit() {
        this.userId = parseInt(this.cookieService.get("userId"));
        this.user.id = this.userId;
    }


  onSubmit() {
    this.address.owner = this.user;
    this.addressService.save(this.address)
        .subscribe(
            result => {
                if (Array.isArray(result)) {
                    this.errors = result;
                } else {
                    this.gotoUserList();
                }
            })
  }

  gotoUserList() {
    this.router.navigate(['/users']);
  }

}
