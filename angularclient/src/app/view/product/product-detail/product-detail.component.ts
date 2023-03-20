import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Product } from '../../../model/product';
import { ProductService } from '../../../service/product.service';
import { ImageService } from '../../../service/image.service';
import { CartService } from '../../../service/cart.service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css']
})
export class ProductDetailComponent implements OnInit {

    product: Product;
    productId: number;
    imageSrc: any;
    items = [];

    constructor(
        private productService: ProductService,
        private router: Router,
        private route: ActivatedRoute,
        private imageService: ImageService,
        private cartService: CartService,
        private sanitizer: DomSanitizer) {
            this.product = new Product();
        }

    ngOnInit() {
        this.productId = this.route.snapshot.params['id'];

        this.getImage();

        this.productService.findOneProduct(this.productId).subscribe(data => {
            this.product = data;
        })
    }

    getImage() {
        this.imageService.getImage({id: this.productId})
            .subscribe(data =>
                {this.imageSrc = this.sanitizer.bypassSecurityTrustResourceUrl(`data:image/png;base64, ${data}`);});
    }

    addToCart(item) {
        if (!this.cartService.itemInCart(item)) {
          item.qtyTotal = 1;
          this.cartService.addToCart(item);
          this.items = [...this.cartService.getItems()];
        }
    }

}
