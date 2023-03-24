import { Component, OnInit } from '@angular/core';
import { Address } from '../../../model/address';
import { AddressService } from '../../../service/address.service';

@Component({
  selector: 'app-address-admin-list',
  templateUrl: './address-admin-list.component.html',
  styleUrls: ['./address-admin-list.component.css']
})
export class AddressAdminListComponent implements OnInit {

    addresses: Address[];

    constructor(private addressService: AddressService) {}

    ngOnInit() {
        this.addressService.findAllForAdmin()
            .subscribe(data => {this.addresses = data});
    }

}
