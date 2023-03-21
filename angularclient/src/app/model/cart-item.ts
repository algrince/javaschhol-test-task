import { PropertyValue } from '../model/property-value';

export class CartItem {
    id: number;
    title: string;
    price: number;
    propertyValues: PropertyValue[];

    quantity: number;
    cost: number;
}
