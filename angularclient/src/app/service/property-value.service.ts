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
}
