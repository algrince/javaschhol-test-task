import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Address } from '../../../model/address';
import { AddressService } from '../../../service/address.service';

@Component({
  selector: 'app-address-delete',
  templateUrl: './address-delete.component.html',
  styleUrls: ['./address-delete.component.css']
})
export class AddressDeleteComponent implements OnInit {

    addressId: number;
    address: Address;

    constructor(
        private addressService: AddressService,
        private router : Router,
        private route : ActivatedRoute) {
            this.address = new Address();
        }

    ngOnInit() {
        this.addressId = this.route.snapshot.params['id'];

        this.addressService.findOneAddress(this.addressId).subscribe(data => {
            this.address = data;
        });

    }

    onSubmit() {
        this.addressService.delete(this.addressId).subscribe(result => this.gotoHomepage());
    }

    gotoHomepage() {
        this.router.navigate(['/home']);
    }

}
