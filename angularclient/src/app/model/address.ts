import { User } from '../model/user';

export class Address {
    id: number;
    country: string;
    city: string;
    postalCode: number;
    street: string;
    home: number;
    apartment: number;
    door: string;
    owner: User;
    deleted: boolean;
}