import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Property } from '../../../model/property';
import { PropertyService } from '../../../service/property.service';
import { PropertyValue } from '../../../model/property-value';
import { PropertyValueService } from '../../../service/property-value.service';

@Component({
  selector: 'app-property-value-create',
  templateUrl: './property-value-create.component.html',
  styleUrls: ['./property-value-create.component.css']
})
export class PropertyValueCreateComponent implements OnInit {

    properties: Property[];
    propertyValue: PropertyValue;
    errors: string[];

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private propertyService: PropertyService,
        private propertyValueService: PropertyValueService) {
            this.propertyValue = new PropertyValue();
        }

    ngOnInit() {

        this.propertyService.findAll()
            .subscribe(data => {this.properties = data});
    }

    onSubmit() {
        this.propertyValueService.save(this.propertyValue)
            .subscribe(
                result => {
                    if (Array.isArray(result)) {
                        this.errors = result;
                    } else {
                        this.gotoPropertyValuesList();
                    }
                })
    }

    gotoPropertyValuesList() {
        this.router.navigate(['/admin/propertyValues']);
    }
}
