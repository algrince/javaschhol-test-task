import { Component, OnInit } from '@angular/core';
import { Product } from '../model/product';
import { Category } from '../model/category';
import { ProductService } from '../service/product.service';
import { CategoryService } from '../service/category.service';
import { NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

    products: Product[];
    categories: Category[];
    totalElements = 0;
    page = 0;
    size = 3;

    constructor(
        private productService: ProductService,
        private categoryService: CategoryService) {
    }

    ngOnInit() {
        this.getProducts({page: this.page, size: this.size});

        this.categoryService.findAll()
            .subscribe(data => {this.categories = data;})
    }

    private getProducts(request) {
        this.productService.findAll(request)
            .subscribe(data => {
                this.products = data['content'];
                this.totalElements = data['totalElements'];
            });
    }

    public onPageChange(pageNum: number): void {
        this.getProducts({page: (pageNum - 1), size: "3"})
    }

}
