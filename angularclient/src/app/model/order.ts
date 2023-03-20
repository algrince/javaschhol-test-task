import { Address } from '../model/address';
import { DeliveryMethod } from '../model/delivery-method';
import { PaymentMethod } from '../model/payment-method';
import { PaymentStatus } from '../model/payment-status';
import { OrderStatus } from '../model/order-status';

export class Order {

    id: number;
    orderSum: number;
    address: Address;
    deliveryMethod = DeliveryMethod;
    paymentMethod = PaymentMethod;
    paymentStatus = PaymentStatus;
    orderStatus = OrderStatus;

}
