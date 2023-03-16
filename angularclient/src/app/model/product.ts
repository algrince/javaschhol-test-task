import { Category } from '../model/category';
import { PropertyValue } from '../model/property-value';

export class Product {
    id: number;
    title: string;
    price: number;
    stock: number;
    volume: number;
    weight: number;
    category: Category;
    propertyValues: PropertyValue[];
}
