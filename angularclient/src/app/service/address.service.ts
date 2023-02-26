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

    public update(id: number, address: Address) {
        const updateUrl = `${this.addressesUrl}/${id}`;
        return this.http.put<Address>(updateUrl, address);
    }

    public delete(id: number) {
        const deleteUrl = `${this.addressesUrl}/${id}`;
        return this.http.delete<Address>(deleteUrl);
    }

    public findAll(): Observable<Address[]> {
        return this.http.get<Address[]>(this.addressesUrl);
    }

    public findOneAddress(id: number): Observable<Address> {
        const url = `${this.addressesUrl}/${id}`;
        return this.http.get<Address>(url);
    }

}