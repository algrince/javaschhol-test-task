import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Category } from '../../../model/category';
import { CategoryService } from '../../../service/category.service';

@Component({
  selector: 'app-category-create',
  templateUrl: './category-create.component.html',
  styleUrls: ['./category-create.component.css']
})
export class CategoryCreateComponent {

    category: Category;
    errors: string[];

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private categoryService: CategoryService) {
            this.category = new Category();
        }

    onSubmit() {
        this.categoryService.save(this.category)
            .subscribe(
                result => {
                    if (Array.isArray(result)) {
                        this.errors = result;
                    } else {
                        this.gotoCategory();
                    }
                })

    }

    gotoCategory() {
        this.router.navigate(['/admin/categories']);
    }
}
