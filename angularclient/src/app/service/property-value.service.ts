import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { PropertyValue } from '../model/property-value';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PropertyValueService {

    private propertyValuesUrl: string;

    constructor(private http: HttpClient) {
        this.propertyValuesUrl = 'http://localhost:8080/propertyValues';
    }

    public findAll(): Observable<PropertyValue[]> {
        return this.http.get<PropertyValue[]>(this.propertyValuesUrl);
    }

    public findOnePropertyValue(id: number): Observable<PropertyValue> {
        const url = `${this.propertyValuesUrl}/${id}`;
        return this.http.get<PropertyValue>(url);
    }

    public save(propertyValue: PropertyValue) {
        return this.http.post<PropertyValue>(this.propertyValuesUrl, propertyValue);
    }

    public update(id: number, propertyValue: PropertyValue) {
        const updateUrl = `${this.propertyValuesUrl}/${id}`;
        return this.http.put<PropertyValue>(updateUrl, propertyValue);
    }

    public delete(id: number) {
        const deleteUrl = `${this.propertyValuesUrl}/${id}`;
        return this.http.delete<PropertyValue>(deleteUrl);
    }
}
