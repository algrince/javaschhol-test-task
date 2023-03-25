import { Order } from '../model/order';
import { Product } from '../model/product';

export class OrderProduct {
    productId: number;
    quantity: number;
    product: Product;
}
