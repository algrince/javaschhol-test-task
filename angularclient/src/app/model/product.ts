import { Category } from '../model/category';

export class Product {
    id: number;
    title: string;
    price: number;
    stock: number;
    volume: number;
    weight: number;
    category: Category;
}
