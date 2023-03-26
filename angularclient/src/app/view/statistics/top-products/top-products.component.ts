import { Component, OnInit } from '@angular/core';
import { ProductStat } from '../../../model/product-stat';
import { StatisticsService } from '../../../service/statistics.service';

@Component({
  selector: 'app-top-products',
  templateUrl: './top-products.component.html',
  styleUrls: ['./top-products.component.css']
})
export class TopProductsComponent implements OnInit {

    productStat: ProductStat;
    productStats: ProductStat[];

    constructor(private statisticsService: StatisticsService) {
        this.productStat = new ProductStat();
    }

    ngOnInit() {
        this.statisticsService.getTop10Products()
            .subscribe(data => {this.productStats = data});
    }

}
