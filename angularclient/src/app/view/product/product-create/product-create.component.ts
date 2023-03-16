import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from '../../../model/product';
import { Category } from '../../../model/category';
import { Property } from '../../../model/property';
import { PropertyValue } from '../../../model/property-value';
import { ProductService } from '../../../service/product.service';
import { CategoryService } from '../../../service/category.service';
import { PropertyService } from '../../../service/property.service';
import { PropertyValueService } from '../../../service/property-value.service';

@Component({
  selector: 'app-product-create',
  templateUrl: './product-create.component.html',
  styleUrls: ['./product-create.component.css']
})
export class ProductCreateComponent implements OnInit {

    product: Product;
    categories: Category[];
    property: Property;
    properties: Property[];
    propertyValue: PropertyValue;
    propertyValues: PropertyValue[];

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private productService: ProductService,
        private categoryService: CategoryService,
        private propertyService: PropertyService,
        private propertyValueService: PropertyValueService) {
            this.product = new Product();
        }

    ngOnInit() {
        this.categoryService.findAll()
            .subscribe(data => {this.categories = data});

        this.propertyService.findAll()
            .subscribe(data => {this.properties = data});

        this.propertyValueService.findAll()
            .subscribe(data => {this.propertyValues = data});
    }

    onSubmit() {
        this.productService.save(this.product)
            .subscribe(result => this.gotoProductList());
    }

    gotoProductList() {
        this.router.navigate(['/products']);
    }

}
