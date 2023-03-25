import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Product } from '../../../model/product';
import { CartItem } from '../../../model/cart-item';
import { ProductService } from '../../../service/product.service';
import { ImageService } from '../../../service/image.service';
import { CartService } from '../../../service/cart.service';
import { CartItemService } from '../../../service/cart-item.service';
import { CookieService } from 'ngx-cookie-service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css']
})
export class ProductDetailComponent implements OnInit {

    product: Product;
    cartItem: CartItem;
    productId: number;
    imageSrc: any;
    items = [];
    item: CartItem;
    role: string;
    roleExists: boolean;

    constructor(
        private productService: ProductService,
        private router: Router,
        private route: ActivatedRoute,
        private imageService: ImageService,
        private cartService: CartService,
        private cartItemService: CartItemService,
        private sanitizer: DomSanitizer,
        private cookieService: CookieService,) {
            this.product = new Product();
        }

    ngOnInit() {
        this.productId = this.route.snapshot.params['id'];

        this.getImage();

        this.productService.findOneProduct(this.productId).subscribe(data => {
            this.product = data;
        });

        this.roleExists = this.cookieService.check("userRole");
        this.role = this.cookieService.get("userRole");
    }

    getImage() {
        this.imageService.getImage({id: this.productId})
            .subscribe(data =>
                {this.imageSrc = this.sanitizer.bypassSecurityTrustResourceUrl(`data:image/png;base64, ${data}`);});
    }

    addToCart(product) {

        this.item = this.cartItemService.mapFromProduct(product);

        if (!this.cartService.itemInCart(this.item)) {
          this.item.quantity = 1;
          this.cartService.addToCart(this.item);
          this.items = [...this.cartService.getItems()];
        }
    }

}
