import { Component, OnInit, ViewChildren, ElementRef, QueryList } from '@angular/core';
import { AuthenticationService } from './service/authentication.service';
import { CartService } from './service/cart.service';

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

  constructor(public authenticationService: AuthenticationService,
  private cartService: CartService) {
    this.title = 'The Store';
  }

  ngOnInit() {
    this.cartService.loadCart();
    this.items = this.cartService.getItems();
  }

    removeFromCart(item) {
      this.cartService.removeItem(item);
      this.items = this.cartService.getItems();
    }

  get total() {
    return this.items.reduce(
      (sum, x) => ({
        qtyTotal: 1,
        price: sum.price + x.qtyTotal * x.price
      }),
      { qtyTotal: 1, price: 0 }
    ).price;
  }

  changeSubtotal(item, index) {
    const qty = item.qtyTotal;
    const amt = item.price;
    const subTotal = amt * qty;

    this.subTotalItems.toArray()[
      index
    ].nativeElement.innerHTML = subTotal;
    this.cartService.saveCart();
  }

}
