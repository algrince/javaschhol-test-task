import { Component, OnInit, ViewChildren, ElementRef, QueryList } from '@angular/core';
import { AuthenticationService } from './service/authentication.service';
import { CartService } from './service/cart.service';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
    @ViewChildren("subTotalWrap") subTotalItems: QueryList<ElementRef>;
    @ViewChildren("subTotalWrap_existing") subTotalItems_existing: QueryList<ElementRef>;

  title: string;
  isHidden = false;
  items = [];
  role: string;
  roleExists: boolean;

  constructor(public authenticationService: AuthenticationService,
  private cartService: CartService,
  private cookieService: CookieService) {
    this.title = 'The Store';
  }

  ngOnInit() {
    this.cartService.loadCart();
    this.items = this.cartService.getItems();
    this.roleExists = this.cookieService.check("userRole");
    this.role = this.cookieService.get("userRole");
  }

  buildLink() {
    if (this.cookieService.check('userId')) {
        const userId = this.cookieService.get('userId');
        return `./users/${userId}`;
    } else {
        return `./login`;
    }
  }

    removeFromCart(item) {
      this.cartService.removeItem(item);
      this.items = this.cartService.getItems();
    }

  get total() {
    return this.items.reduce(
      (sum, x) => ({
        quantity: 1,
        price: sum.price + x.quantity * x.price
      }),
      { quantity: 1, price: 0 }
    ).price;
  }

  changeSubtotal(item, index) {
    const qty = item.quantity;
    const amt = item.price;
    const subTotal = amt * qty;

    this.subTotalItems.toArray()[
      index
    ].nativeElement.innerHTML = subTotal;
    this.cartService.saveCart();
  }

}
