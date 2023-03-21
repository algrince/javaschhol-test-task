import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Product } from '../../../model/product';
import { Category } from '../../../model/category';
import { ProductService } from '../../../service/product.service';
import { CategoryService } from '../../../service/category.service';
import { ImageService } from '../../../service/image.service';
import { CookieService } from 'ngx-cookie-service';
import { NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-category-page',
  templateUrl: './category-page.component.html',
  styleUrls: ['./category-page.component.css']
})
export class CategoryPageComponent implements OnInit{

    products: Product[];
    categories: Category[];
    category: Category;
    categoryId: number;
    totalElements = 0;
    page = 0;
    size = 9;
    sortField = "id";
    sortDir = "ASC";
    imageSrc: any;
    minPrice: any;
    maxPrice: any;
    role: string;
    roleExists: boolean;

    constructor(
        private productService: ProductService,
        private categoryService: CategoryService,
        private imageService: ImageService,
        private cookieService: CookieService,
        private sanitizer: DomSanitizer,
        private router: Router,
        private route: ActivatedRoute) {
            this.category = new Category();
    }

    ngOnInit() {
        this.route.paramMap.subscribe(
            (routeParam) => {
                const id = parseInt(routeParam.get("id"));
                this.categoryId = id;
                this.getCategory();

                this.getProducts({category: this.categoryId, page: 0, size: this.size});

                this.categoryService.findAll()
                    .subscribe(data => {this.categories = data})
            }
        )

        this.roleExists = this.cookieService.check("userRole");
        this.role = this.cookieService.get("userRole");
    }

    public getCategory() {
        this.categoryId = this.route.snapshot.params['id'];
        this.categoryService.findOneCategory(this.categoryId)
            .subscribe(data => {this.category = data});
    }

    private getProducts(request) {
        this.getImage();

        this.productService.findAllByCategory(request)
            .subscribe(data => {
                this.products = data['content'];
                this.totalElements = data['totalElements'];
            });
    }

    getImage() {
        this.imageService.getImage({id: 0})
            .subscribe(data =>
                {this.imageSrc = this.sanitizer.bypassSecurityTrustResourceUrl(`data:image/png;base64, ${data}`);});
    }

    setValue() {}

    onSubmit() {
        this.getProducts
            ({page: 0, size: this.size,
            sortField: this.sortField, sortDir: this.sortDir,
            minPrice: this.minPrice, maxPrice: this.maxPrice});
    }

    public onPageChange(pageNum: number): void {
        this.getProducts(
        {category: this.categoryId,
        page: (pageNum - 1), size: this.size,
        sortField: this.sortField, sortDir: this.sortDir})
    }
}
