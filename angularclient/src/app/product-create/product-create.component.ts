import { Component } from '@angular/core';
import { Product } from '../model/product';
import { ProductService } from '../service/product.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-product-create',
  templateUrl: './product-create.component.html',
  styleUrls: ['./product-create.component.css']
})
export class ProductCreateComponent {

    product: Product;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private productService: ProductService) {
            this.product = new Product();
        }

    onSubmit() {
        this.productService.save(this.product).subscribe(result => this.gotoProductList());
    }

    gotoProductList() {
        this.router.navigate(['/products']);
    }

}
