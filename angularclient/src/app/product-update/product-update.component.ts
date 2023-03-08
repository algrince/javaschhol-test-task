import { Component, OnInit } from '@angular/core';
import { Product } from '../model/product';
import { ProductService } from '../service/product.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-product-update',
  templateUrl: './product-update.component.html',
  styleUrls: ['./product-update.component.css']
})
export class ProductUpdateComponent implements OnInit {

    productId: number;
    product: Product;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private productService: ProductService) {
            this.product = new Product();
        }


    ngOnInit() {
        this.productId = this.route.snapshot.params['id'];

        this.productService.findOneProduct(this.productId)
            .subscribe(data => {this.product = data;});
    }

    onSubmit() {
        this.productService.update(this.productId, this.product)
            .subscribe(result => this.gotoProductList());
    }

    gotoProductList() {
        this.router.navigate(['/products']);
    }
}
