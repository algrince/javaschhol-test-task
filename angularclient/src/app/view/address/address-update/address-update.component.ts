import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Address } from '../../../model/address';
import { AddressService } from '../../../service/address.service';

@Component({
  selector: 'app-address-update',
  templateUrl: './address-update.component.html',
  styleUrls: ['./address-update.component.css']
})
export class AddressUpdateComponent implements OnInit {

    addressId: number;
    address: Address;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private addressService: AddressService) {
            this.address = new Address();
        }

    ngOnInit() {
        this.addressId = this.route.snapshot.params['id'];

        this.addressService.findOneAddress(this.addressId)
            .subscribe(data => {this.address = data;});
    }

    onSubmit() {
        this.addressService.update(this.addressId, this.address)
            .subscribe(result => this.gotoHomepage());
    }

    gotoHomepage() {
    this.router.navigate(['/home']);
    }
}
