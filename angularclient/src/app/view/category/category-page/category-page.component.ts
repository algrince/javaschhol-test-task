import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Product } from '../../../model/product';
import { Category } from '../../../model/category';
import { ProductService } from '../../../service/product.service';
import { CategoryService } from '../../../service/category.service';
import { NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-category-page',
  templateUrl: './category-page.component.html',
  styleUrls: ['./category-page.component.css']
})
export class CategoryPageComponent {

    products: Product[];
    categories: Category[];
    category: Category;
    categoryId: number;
    totalElements = 0;
    page = 0;
    size = 3;

    constructor(
        private productService: ProductService,
        private categoryService: CategoryService,
        private router: Router,
        private route: ActivatedRoute) {
            this.category = new Category();
    }

    ngOnInit() {
        this.getCategory();

        this.getProducts({category: this.categoryId, page: this.page, size: this.size});

        this.categoryService.findAll()
            .subscribe(data => {this.categories = data;})
    }

    public getCategory() {
        this.categoryId = this.route.snapshot.params['id'];
        this.categoryService.findOneCategory(this.categoryId)
            .subscribe(data => {this.category = data});
    }

    private getProducts(request) {
        this.productService.findAllByCategory(request)
            .subscribe(data => {
                this.products = data['content'];
                this.totalElements = data['totalElements'];
            });
    }

    public onPageChange(pageNum: number): void {
        this.getProducts({category: this.categoryId ,page: (pageNum - 1), size: "3"})
    }
}
