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

    public findAll(request): Observable<Order[]> {
        const params = request;
        return this.http.get<Order[]>(this.ordersUrl, {params});
    }

    public findAllForAdmin(): Observable<Order[]> {
        const allOrdersUrl = `${this.ordersUrl}/all`
        return this.http.get<Order[]>(allOrdersUrl);
    }

    public findOneOrder(id: number): Observable<Order> {
        const url = `${this.ordersUrl}/${id}`;
        return this.http.get<Order>(url);
    }

    public update(id: number, order: Order) {
        const updateUrl = `${this.ordersUrl}/${id}`;
        return this.http.put<Order>(updateUrl, order);
    }
}
