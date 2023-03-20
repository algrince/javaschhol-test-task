import { Injectable } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ImageService {

    imageSrc: any;
    data: any;

  constructor(
    private http: HttpClient,
    private sanitizer: DomSanitizer) { }

  getImage(request) {
    const params = request;
    const filteredParams = Object.fromEntries(
        Object.entries(params)
          .filter(([key, value]) => value !== undefined)
      ) as { [param: string]: string };

    const httpParams = new HttpParams({ fromObject: filteredParams });

    return this.http.get('http://localhost:8080/images', { responseType: 'text', params: httpParams });
  }


}
