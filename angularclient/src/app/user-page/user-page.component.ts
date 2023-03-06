import { Component, OnInit } from '@angular/core';
import { User } from '../model/user';
import { UserService } from '../service/user.service';
import { Address } from '../model/address';
import { AddressService } from '../service/address.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css']
})
export class UserPageComponent implements OnInit {

  user: User;
  userId: number;
  addresses: Address[];

  constructor(
    private userService : UserService,
    private addressService : AddressService,
    private router : Router,
    private route: ActivatedRoute) {
      this.user = new User();
  }


  ngOnInit() {
    this.userId = this.route.snapshot.params['id'];

    this.userService.findOneUser(this.userId).subscribe(data => {
        this.user = data;
    });

    this.addressService.findAll().subscribe(data => {
        this.addresses = data;
    });
  }
}
