import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Address } from '../../../model/address';
import { User } from '../../../model/user';
import { AddressService } from '../../../service/address.service';
import { OrderService } from '../../../service/order.service';
import { CartService } from '../../../service/cart.service';
import { ProductService } from '../../../service/product.service';
import { Order, OrderStatus, PaymentStatus, PaymentMethod, DeliveryMethod } from '../../../model/order';
import { CookieService } from 'ngx-cookie-service';
import { CartItem } from '../../../model/cart-item';
import { OrderProduct } from '../../../model/order-product';
import { Product } from '../../../model/product';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-order-form',
  templateUrl: './order-form.component.html',
  styleUrls: ['./order-form.component.css']
})

export class OrderFormComponent implements OnInit {

    addresses: Address[];
    items = [];
    order: Order;
    userId: number;
    user: User;
    product: Product;


    constructor(
        private addressService: AddressService,
        private orderService: OrderService,
        private cartService: CartService,
        private productService: ProductService,
        private router: Router,
        private route: ActivatedRoute,
        private cookieService: CookieService) {
            this.order = new Order();
            this.user = new User();
            this.product = new Product();
        }

    ngOnInit() {
        this.items = this.cartService.getItems();

        this.userId = parseInt(this.cookieService.get("userId"));
        this.user.id = this.userId;

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

        const orderProducts: OrderProduct[] = [];
        for (const item of this.items) {
            const orderProduct = new OrderProduct();
            orderProduct.quantity = item.quantity;
            orderProduct.productId = item.id;

            orderProducts.push(orderProduct);
        }
        this.order.orderProducts = orderProducts;



        this.order.orderSum = this.total;

        this.order.user = this.user;

        console.log(this.order);

        this.orderService.save(this.order)
            .subscribe(result => {
                this.cartService.clearCart(this.items);
                this.gotoProductList();
            });

    }


    gotoProductList() {
        this.router.navigate(['/products']);
    }
}
