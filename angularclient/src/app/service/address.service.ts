import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Address } from '../model/address';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class AddressService {

    private addressesUrl: string;

    constructor(private http: HttpClient) {
        this.addressesUrl = 'http://localhost:8080/addresses'
    }

    public save(address: Address) {
        return this.http.post<Address>(this.addressesUrl, address);
    }

}