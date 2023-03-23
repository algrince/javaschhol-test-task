import { Address } from '../model/address';
import { CartItem } from '../model/cart-item';
import { OrderProduct } from '../model/order-product';
import { User } from '../model/user';


export class Order {

    id: number;
    orderSum: number;
    address: Address;
    deliveryMethod: DeliveryMethod;
    paymentMethod: PaymentMethod;
    paymentStatus: PaymentStatus;
    orderStatus: OrderStatus;
    products: CartItem[];
    createdDate: Date;
    user: User;
    orderProducts: OrderProduct[];
}

export enum OrderStatus {
    PENDING_PAYMENT,
    PENDING_SHIPMENT,
    SHIPPED,
    DELIVERED
}

export enum PaymentStatus {
    PENDING,
    PAID
}

export enum PaymentMethod {
    CARD,
    CASH
}

export enum DeliveryMethod {
    STANDARD,
    EXPRESS
}