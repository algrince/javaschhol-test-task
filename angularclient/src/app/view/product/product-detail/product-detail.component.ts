import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Product } from '../../../model/product';
import { ProductService } from '../../../service/product.service';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css']
})
export class ProductDetailComponent implements OnInit {

    product: Product;
    productId: number;

    constructor(
        private productService: ProductService,
        private router: Router,
        private route: ActivatedRoute) {
            this.product = new Product();
        }

    ngOnInit() {
        this.productId = this.route.snapshot.params['id'];

        this.productService.findOneProduct(this.productId).subscribe(data => {
            this.product = data;
        })
    }

}
