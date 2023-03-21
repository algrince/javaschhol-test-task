import { Injectable } from '@angular/core';
import { Product } from '../model/product';
import { CartItem } from '../model/cart-item';

@Injectable({
  providedIn: 'root'
})
export class CartItemService {

    product = Product;
    item = CartItem;

    constructor() { }

    public mapFromProduct(product: Product): CartItem {
        return {
            id: product.id,
            title: product.title,
            price: product.price,
            propertyValues: product.propertyValues,
            quantity: 1,
            cost: product.price
        };
    }

}
