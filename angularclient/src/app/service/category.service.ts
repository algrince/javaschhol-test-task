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

     public save(category: Category) {
        return this.http.post<Category>(this.categoriesUrl, category);
     }

     public update(id: number, category: Category) {
        const updateUrl = `${this.categoriesUrl}/${id}`;
        return this.http.put<Category>(updateUrl, category);
     }

     public delete(id: number) {
        const deleteUrl = `${this.categoriesUrl}/${id}`;
        return this.http.delete<Category>(deleteUrl);
     }

     public restore(id: number) {
         const restoreUrl = `${this.categoriesUrl}/${id}/restore`
         return this.http.put<Category>(restoreUrl, null);
     }
}
