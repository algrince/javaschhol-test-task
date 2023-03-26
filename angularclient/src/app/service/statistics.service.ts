import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RevenueResult } from '../model/revenue-result'
import { ProductStat } from '../model/product-stat';

@Injectable({
  providedIn: 'root'
})
export class StatisticsService {

    private statisticsRev: string;

    constructor(private http: HttpClient) {
        this.statisticsRev = 'http://localhost:8080/statistics';
    }

    public getMonthlyRevenue(request): Observable<RevenueResult[]> {
        const params = request;
        const monthlyRevUrl = `${this.statisticsRev}/monthly_revenue`;
        return this.http.get<RevenueResult[]>(monthlyRevUrl, {params});
    }

    public getWeeklyRevenue(request): Observable<RevenueResult[]> {
        const params = request;
        const weeklyRevUrl = `${this.statisticsRev}/weekly_revenue`;
        return this.http.get<RevenueResult[]>(weeklyRevUrl, {params});
    }

    public getTop10Products(): Observable<ProductStat[]> {
        const topProductsUrl = `${this.statisticsRev}/top_products`;
        return this.http.get<ProductStat[]>(topProductsUrl);
    }
}
