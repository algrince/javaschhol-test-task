import { Component, OnInit } from '@angular/core';
import { Product } from '../model/product';
import { ProductService } from '../service/product.service';
import { NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

    products: Product[];
    page = 1;
    itemsPerPage = 3;
    pageSize: number;

    constructor(private productService: ProductService) {
    }

    ngOnInit() {
        this.productService.findAll().subscribe(data => {
            this.products = data;
        })
    }

    public onPageChange(pageNum: number): void {
        this.pageSize = this.itemsPerPage*(pageNum - 1);
    }

}
