import { Component, OnInit } from '@angular/core';
import { Product } from '../model/product';
import { ProductService } from '../service/product.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-product-delete',
  templateUrl: './product-delete.component.html',
  styleUrls: ['./product-delete.component.css']
})
export class ProductDeleteComponent implements OnInit {

    productId: number;
    product: Product;

    constructor(
        private productService: ProductService,
        private router: Router,
        private route: ActivatedRoute) {
            this.product = new Product();
        }

    ngOnInit() {
        this.productId = this.route.snapshot.params['id'];

        this.productService.findOneProduct(this.productId)
            .subscribe(data => {this.product = data;});
    }


    onSubmit() {
        this.productService.delete(this.productId)
            .subscribe(result => this.gotoProductList());
    }

    gotoProductList() {
        this.router.navigate(['/products']);
    }
}
