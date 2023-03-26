import { Component, OnInit } from '@angular/core';
import { Category } from '../../../model/category';
import { CategoryService } from '../../../service/category.service';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-category-admin-list',
  templateUrl: './category-admin-list.component.html',
  styleUrls: ['./category-admin-list.component.css']
})
export class CategoryAdminListComponent implements OnInit {

    categories: Category[];
    role: string;

    constructor(
    private categoryService: CategoryService,
    private cookieService: CookieService) {}

    ngOnInit() {
        this.categoryService.findAll()
            .subscribe(data => {this.categories = data});

        this.role = this.cookieService.get("userRole");
    }

    restore(categoryId: number) {
        this.categoryService.restore(categoryId)
            .subscribe(result => location.reload());
    }

}
