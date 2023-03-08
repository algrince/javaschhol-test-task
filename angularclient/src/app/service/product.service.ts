import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Product } from '../model/product';
import { Category } from '../model/category';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

   private productsUrl: string;

   constructor(private http: HttpClient) {
        this.productsUrl = 'http://localhost:8080/products'
    }


   public findAll(request): Observable<Product[]> {
        const params = request;
        return this.http.get<Product[]>(this.productsUrl, {params});
   }

   public findAllByCategory(request): Observable<Product[]> {
        const params = request;
        const categorizedProductsUrl = 'http://localhost:8080/products'
        return this.http.get<Product[]>(categorizedProductsUrl, {params});
   }

   public findOneProduct(id: number): Observable<Product> {
        const detailUrl = `${this.productsUrl}/${id}`;
        return this.http.get<Product>(detailUrl);
   }

   public save(product: Product) {
        return this.http.post<Product>(this.productsUrl, product);
   }

   public update(id: number, product: Product) {
        const updateUrl = `${this.productsUrl}/${id}`;
        return this.http.put<Product>(updateUrl, product);
   }

   public delete(id: number) {
        const deleteUrl = `${this.productsUrl}/${id}`;
        return this.http.delete<Product>(deleteUrl);
   }
}
