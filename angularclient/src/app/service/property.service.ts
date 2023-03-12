import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Property } from '../model/property';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PropertyService {

    private propertiesUrl: string;

    constructor(private http: HttpClient) {
        this.propertiesUrl = 'http://localhost:8080/properties'
    }

    public findAll(): Observable<Property[]> {
        return this.http.get<Property[]>(this.propertiesUrl);
    }

    public findOneProperty(id: number): Observable<Property> {
        const url = `${this.propertiesUrl}/${id}`;
        return this.http.get<Property>(url);
    }

    public save(property: Property) {
        return this.http.post<Property>(this.propertiesUrl, property);
    }

    public update(id: number, property: Property) {
        const updateUrl = `${this.propertiesUrl}/${id}`;
        return this.http.put<Property>(updateUrl, property);
    }

    public delete(id: number) {
        const deleteUrl = `${this.propertiesUrl}/${id}`;
        return this.http.delete<Property>(deleteUrl);
    }

}
