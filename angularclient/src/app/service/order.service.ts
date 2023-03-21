import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Order } from '../model/order';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

    ordersUrl: string;

    constructor(private http: HttpClient) {
        this.ordersUrl = 'http://localhost:8080/orders'
    }

    public save(order: Order) {
        return this.http.post<Order>(this.ordersUrl, order);
    }
}
