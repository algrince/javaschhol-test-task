import { Order } from '../model/order';
import { Product } from '../model/product';

export class OrderProduct {
    addedProductId: number;
    quantity: number;
    product: Product;
}
