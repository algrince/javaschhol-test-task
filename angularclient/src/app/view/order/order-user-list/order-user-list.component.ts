import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Order, OrderStatus, PaymentStatus, PaymentMethod, DeliveryMethod }  from '../../../model/order';
import { OrderService } from '../../../service/order.service';


@Component({
  selector: 'app-order-user-list',
  templateUrl: './order-user-list.component.html',
  styleUrls: ['./order-user-list.component.css']
})
export class OrderUserListComponent implements OnInit {

    orders: Order[];
    order: Order;
    orderId: number;
    role: string;
    userId: number;
    newOrder: Order;

    constructor(
        private orderService: OrderService,
        private router: Router,
        private route: ActivatedRoute) {

        }

    ngOnInit() {
        this.userId = this.route.snapshot.params['id'];

        this.orderService.findAll({user: this.userId})
            .subscribe(data => {this.orders = data});

    }

    getOrderStatusDisplayValue(status: OrderStatus): string {
        return status.toString().toLowerCase().replace('_', ' ');
    }

    getDeliveryMethodDisplayValue(method: DeliveryMethod): string {
        return method.toString().toLowerCase().replace('_', ' ');
    }

    getNumericValue(status: PaymentStatus): number {
        if (status.toString() == "PENDING") {
            return 0;
        }
        return 1;
    }


}
