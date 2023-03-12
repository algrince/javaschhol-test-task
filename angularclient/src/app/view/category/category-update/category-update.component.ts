import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Category } from '../../../model/category';
import { CategoryService } from '../../../service/category.service';

@Component({
  selector: 'app-category-update',
  templateUrl: './category-update.component.html',
  styleUrls: ['./category-update.component.css']
})
export class CategoryUpdateComponent implements OnInit {

    categoryId: number;
    category: Category;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private categoryService: CategoryService) {
            this.category = new Category();
        }

    ngOnInit() {
        this.categoryId = this.route.snapshot.params["id"];

        this.categoryService.findOneCategory(this.categoryId)
            .subscribe(data => {this.category = data});
    }

    onSubmit() {
        this.categoryService.update(this.categoryId, this.category)
            .subscribe(result => this.gotoProductList());
    }

    gotoProductList() {
        this.router.navigate(['/products']);
    }
}
