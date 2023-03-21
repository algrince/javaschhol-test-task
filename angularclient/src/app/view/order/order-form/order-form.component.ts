import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Address } from '../../../model/address';
import { AddressService } from '../../../service/address.service';
import { OrderService } from '../../../service/order.service';
import { CartService } from '../../../service/cart.service';
import { Order, OrderStatus, PaymentStatus, PaymentMethod, DeliveryMethod } from '../../../model/order';


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
        private orderService: OrderService,
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

        this.order.orderStatus = OrderStatus.PENDING_PAYMENT;
        this.order.paymentStatus = PaymentStatus.PENDING;

    }

      get total() {
        return this.items.reduce(
          (sum, x) => ({
            quantity: 1,
            price: sum.price + x.quantity * x.price
          }),
          { quantity: 1, price: 0 }
        ).price;
      }

    setOrderAsPaid() {
        this.order.orderStatus = OrderStatus.PENDING_SHIPMENT;
        this.order.paymentStatus = PaymentStatus.PAID;
    }



    onSubmit() {
        this.order.products = this.items;
        this.order.orderSum = this.total;

        this.cartService.clearCart(this.items);

        this.orderService.save(this.order)
            .subscribe(result => this.gotoProductList());

    }

    gotoProductList() {
        this.router.navigate(['/products']);
    }
}
