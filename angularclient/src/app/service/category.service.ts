import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Category } from '../model/category';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

    private categoriesUrl: string;

    constructor(private http: HttpClient) {
        this.categoriesUrl = 'http://localhost:8080/categories';
    }

     public findAll(): Observable<Category[]> {
          return this.http.get<Category[]>(this.categoriesUrl);
     }

     public findOneCategory(id: number): Observable<Category> {
        const url = `${this.categoriesUrl}/${id}`;
        return this.http.get<Category>(url);
     }
}
