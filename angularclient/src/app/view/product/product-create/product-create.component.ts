import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from '../../../model/product';
import { Category } from '../../../model/category';
import { ProductService } from '../../../service/product.service';
import { CategoryService } from '../../../service/category.service';

@Component({
  selector: 'app-product-create',
  templateUrl: './product-create.component.html',
  styleUrls: ['./product-create.component.css']
})
export class ProductCreateComponent implements OnInit {

    product: Product;
    categories: Category[];

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private productService: ProductService,
        private categoryService: CategoryService) {
            this.product = new Product();
        }

    ngOnInit() {
        this.categoryService.findAll()
            .subscribe(data => {this.categories = data})
    }

    onSubmit() {
        this.productService.save(this.product)
            .subscribe(result => this.gotoProductList());
    }

    gotoProductList() {
        this.router.navigate(['/products']);
    }

}
