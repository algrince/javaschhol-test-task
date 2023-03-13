import { Injectable } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ImageService {

    imageSrc: any;
    data: any;

  constructor(
    private http: HttpClient,
    private sanitizer: DomSanitizer) { }

  getImage() {
    return this.http.get('http://localhost:8080/images', { responseType: 'text' });
  }


}
