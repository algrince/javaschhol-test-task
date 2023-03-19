import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Property } from '../../../model/property';
import { PropertyService } from '../../../service/property.service';
import { PropertyValue } from '../../../model/property-value';
import { PropertyValueService } from '../../../service/property-value.service';

@Component({
  selector: 'app-property-value-update',
  templateUrl: './property-value-update.component.html',
  styleUrls: ['./property-value-update.component.css']
})
export class PropertyValueUpdateComponent implements OnInit {

    propertyValueId: number;
    propertyValue: PropertyValue;
    properties: Property[];
    errors: string[];

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private propertyService: PropertyService,
        private propertyValueService: PropertyValueService) {
            this.propertyValue = new PropertyValue();
        }

    ngOnInit() {
        this.propertyValueId = this.route.snapshot.params['id'];

        this.propertyValueService.findOnePropertyValue(this.propertyValueId)
            .subscribe(data => {this.propertyValue = data});

        this.propertyService.findAll()
            .subscribe(data => {this.properties = data});
    }

    onSubmit() {
        this.propertyValueService.update(this.propertyValueId, this.propertyValue)
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
        this.router.navigate(['/propertyValues']);
    }
}
