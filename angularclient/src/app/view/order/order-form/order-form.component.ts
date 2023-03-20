import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { Address } from '../../../model/address';
import { AddressService } from '../../../service/address.service';
import { CartService } from '../../../service/cart.service';
import { Order } from '../../../model/order';

@Component({
  selector: 'app-order-form',
  templateUrl: './order-form.component.html',
  styleUrls: ['./order-form.component.css']
})
export class OrderFormComponent implements OnInit {

    userId: number = 25;
    addresses: Address[];
    items = [];
    order: Order;

    constructor(
        private addressService: AddressService,
        private cartService: CartService,
        private router: Router,
        private route: ActivatedRoute) {
            this.order = new Order();
        }

    ngOnInit() {
        this.items = this.cartService.getItems();

        this.addressService.findAll({user: this.userId})
        .subscribe(data =>
            this.addresses = data);

    }

    onSubmit() {
        
    }

}
