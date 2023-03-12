import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Category } from '../../../model/category';
import { CategoryService } from '../../../service/category.service';

@Component({
  selector: 'app-category-delete',
  templateUrl: './category-delete.component.html',
  styleUrls: ['./category-delete.component.css']
})
export class CategoryDeleteComponent implements OnInit {

    categoryId: number;
    category: Category;

    constructor(
        private categoryService: CategoryService,
        private router: Router,
        private route: ActivatedRoute) {
            this.category = new Category();
        }

    ngOnInit() {
        this.categoryId = this.route.snapshot.params['id'];

        this.categoryService.findOneCategory(this.categoryId)
            .subscribe(data => {this.category = data});
    }

    onSubmit() {
        this.categoryService.delete(this.categoryId)
            .subscribe(result => this.gotoProductList());
    }

    gotoProductList() {
        this.router.navigate(['/products']);
    }
}
