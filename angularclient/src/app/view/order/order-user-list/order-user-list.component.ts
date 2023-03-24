import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { DatePipe } from '@angular/common';
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

    constructor(
        private orderService: OrderService,
        private datePipe: DatePipe,
        private router: Router,
        private route: ActivatedRoute) {}

    ngOnInit() {
        this.orderService.findAll()
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
