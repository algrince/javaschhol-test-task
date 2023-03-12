import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from '../../../model/product';
import { Category } from '../../../model/category';
import { ProductService } from '../../../service/product.service';
import { CategoryService } from '../../../service/category.service';


@Component({
  selector: 'app-product-update',
  templateUrl: './product-update.component.html',
  styleUrls: ['./product-update.component.css']
})
export class ProductUpdateComponent implements OnInit {

    productId: number;
    product: Product;
    categories: Category[];
    category: Category;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private productService: ProductService,
        private categoryService: CategoryService) {
            this.product = new Product();
        }


    ngOnInit() {
        this.productId = this.route.snapshot.params['id'];

        this.productService.findOneProduct(this.productId)
            .subscribe(data => {this.product = data});

        this.categoryService.findAll()
                    .subscribe(data => {this.categories = data})
    }

    onSubmit() {
        this.productService.update(this.productId, this.product)
            .subscribe(result => this.gotoProductList());
    }

    gotoProductList() {
        this.router.navigate(['/products']);
    }
}
