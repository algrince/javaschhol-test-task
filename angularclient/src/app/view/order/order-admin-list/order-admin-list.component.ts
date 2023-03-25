import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Order, OrderStatus, PaymentStatus, PaymentMethod, DeliveryMethod }  from '../../../model/order';
import { OrderService } from '../../../service/order.service';

@Component({
  selector: 'app-order-admin-list',
  templateUrl: './order-admin-list.component.html',
  styleUrls: ['./order-admin-list.component.css']
})
export class OrderAdminListComponent implements OnInit {

    orders: Order[];
    order: Order;
    orderId: number;

    constructor(
        private orderService: OrderService,
        private router: Router,
        private route: ActivatedRoute) {}

    ngOnInit() {

        this.orderService.findAllForAdmin()
            .subscribe(data => {this.orders = data});

    }

    getOrderStatusDisplayValue(status: OrderStatus): string {
        return status.toString().toLowerCase().replace('_', ' ');
    }

    getDeliveryMethodDisplayValue(method: DeliveryMethod): string {
        return method.toString().toLowerCase().replace('_', ' ');
    }

    getPaymentStatusDisplayValue(status: PaymentStatus): string {
        return status.toString().toLowerCase().replace('_', ' ');
    }

    getPaymentMethodDisplayValue(method: PaymentMethod): string {
        return method.toString().toLowerCase().replace('_', ' ');
    }

}
