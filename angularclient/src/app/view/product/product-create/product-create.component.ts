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
import { FormGroup, FormControl, FormArray, FormBuilder } from '@angular/forms';
import { Subscription } from 'rxjs';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatRadioModule } from '@angular/material/radio';
import { MatTableModule } from '@angular/material/table';


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
    propertyValues: PropertyValue[];
    selectedValues: PropertyValue[] = [];
    selectedPropertyValues: any[];
    propertiesForm: FormGroup;
    subscription: Subscription;


    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private productService: ProductService,
        private categoryService: CategoryService,
        private propertyService: PropertyService,
        private propertyValueService: PropertyValueService,
        private formBuilder: FormBuilder) {
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
        this.product.propertyValues = this.selectedValues;

        this.productService.save(this.product)
            .subscribe(result => this.gotoProductList());
    }

    gotoProductList() {
        this.router.navigate(['/products']);
    }

    updateSelectedValues(selectedValueName: string) {
      const selectedPropertyValue = this.propertyValues.find(pv => pv.propertyValue === selectedValueName);
      if (selectedPropertyValue) {
        const index = this.selectedValues.findIndex(pv => pv.id === selectedPropertyValue.id);
        if (index > -1) {
          // If the property value is already in the array, remove it
          this.selectedValues.splice(index, 1);
        } else {
          // If the property value is not in the array, add it
          this.selectedValues.push(selectedPropertyValue);
        }
      }
    }

}
