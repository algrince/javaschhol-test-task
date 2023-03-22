import { Component, OnInit } from '@angular/core';
import { Product } from '../../../model/product';
import { Category } from '../../../model/category';
import { PropertyValue } from '../../../model/property-value';
import { ProductService } from '../../../service/product.service';
import { CategoryService } from '../../../service/category.service';
import { ImageService } from '../../../service/image.service';
import { PropertyValueService } from '../../../service/property-value.service';
import { NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import { DomSanitizer } from '@angular/platform-browser';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

    products: Product[];
    categories: Category[];
    propertyValues: PropertyValue[];
    selectedValues: number[] = [];
    totalElements = 0;
    page = 0;
    size = 9;
    sortField = "id";
    sortDir = "ASC";
    imageSrc: any;
    minPrice?: number;
    maxPrice?: number;
    role: string;
    roleExists: boolean;

    constructor(
        private productService: ProductService,
        private categoryService: CategoryService,
        private imageService: ImageService,
        private sanitizer: DomSanitizer,
        private cookieService: CookieService,
        private propertyValueService: PropertyValueService) {
        }

    ngOnInit() {
        this.getProducts({page: this.page, size: this.size});

        this.categoryService.findAll()
            .subscribe(data => {this.categories = data})

        this.propertyValueService.findAll()
            .subscribe(data => {this.propertyValues = data});

        this.roleExists = this.cookieService.check("userRole");
        this.role = this.cookieService.get("userRole");
    }

    getImage() {
        this.imageService.getImage({id: 0})
            .subscribe(data =>
                {this.imageSrc = this.sanitizer
                    .bypassSecurityTrustResourceUrl(`data:image/png;base64, ${data}`);});
    }

    onSubmit() {

        this.getProducts
            ({page: 0, size: this.size,
            sortField: this.sortField, sortDir: this.sortDir,
            minPrice: this.minPrice, maxPrice: this.maxPrice,
            prValues: this.selectedValues});
    }

    private getProducts(request) {

        this.getImage();

        this.productService.findAll(request)
            .subscribe(data => {
                this.products = data['content'];
                this.totalElements = data['totalElements'];
            });
    }

    public onPageChange(pageNum: number): void {
        this.getProducts(
            {page: (pageNum - 1), size: this.size,
            sortField: this.sortField, sortDir: this.sortDir,
            minPrice: this.minPrice, maxPrice: this.maxPrice});
    }

    updateSelectedValues(selectedValueId: number) {
      if (selectedValueId) {
        const index = this.selectedValues.findIndex(pv => pv === selectedValueId);
        if (index > -1) {
          // If the property value is already in the array, remove it
          this.selectedValues.splice(index, 1);
        } else {
          // If the property value is not in the array, add it
          this.selectedValues.push(selectedValueId);
        }
      }
    }

}
