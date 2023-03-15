import { Component, OnInit } from '@angular/core';
import { Product } from '../../../model/product';
import { Category } from '../../../model/category';
import { ProductService } from '../../../service/product.service';
import { CategoryService } from '../../../service/category.service';
import { ImageService } from '../../../service/image.service';
import { NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

    products: Product[];
    categories: Category[];
    totalElements = 0;
    page = 0;
    size = 8;
    sortField = "id";
    sortDir = "ASC";
    imageSrc: any;
    minPrice: any;
    maxPrice: any;

    constructor(
        private productService: ProductService,
        private categoryService: CategoryService,
        private imageService: ImageService,
        private sanitizer: DomSanitizer) {
    }

    ngOnInit() {
        this.getProducts({page: this.page, size: this.size});

        this.categoryService.findAll()
            .subscribe(data => {this.categories = data;})
    }

    getImage() {
        this.imageService.getImage()
            .subscribe(data =>
                {this.imageSrc = this.sanitizer
                    .bypassSecurityTrustResourceUrl(`data:image/png;base64, ${data}`);});
    }

    onSubmit() {
        this.getProducts
            ({page: 0, size: this.size,
            sortField: this.sortField, sortDir: this.sortDir,
            minPrice: this.minPrice, maxPrice: this.maxPrice});
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
            minPrice: this.minPrice, maxPrice: this.maxPrice})
    }

}
