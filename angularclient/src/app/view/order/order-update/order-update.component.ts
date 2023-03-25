import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Order, OrderStatus, PaymentStatus, PaymentMethod, DeliveryMethod }  from '../../../model/order';
import { OrderService } from '../../../service/order.service';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-order-update',
  templateUrl: './order-update.component.html',
  styleUrls: ['./order-update.component.css']
})
export class OrderUpdateComponent implements OnInit {

    order: Order;
    orderId: number;
    role: string;
    roleExists: boolean;

    constructor(
        private orderService: OrderService,
        private router: Router,
        private route: ActivatedRoute,
        private cookieService: CookieService) {
            this.order = new Order();
        }

    ngOnInit() {
        this.orderId = this.route.snapshot.params['id']

        this.orderService.findOneOrder(this.orderId)
            .subscribe(data => {this.order = data});

        this.roleExists = this.cookieService.check("userRole");
        this.role = this.cookieService.get("userRole");
    }

    setOrderAsPaid() {
        this.order.orderStatus = OrderStatus.PENDING_SHIPMENT;
        this.order.paymentStatus = PaymentStatus.PAID;
    }

    onSubmit() {
        this.orderService.update(this.orderId, this.order)
            .subscribe(result => this.gotoHomepage());
    }

    gotoHomepage() {
        this.router.navigate(['/admin/orders']);
    }
}
