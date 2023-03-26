import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Property } from '../../../model/property';
import { PropertyService } from '../../../service/property.service';

@Component({
  selector: 'app-property-update',
  templateUrl: './property-update.component.html',
  styleUrls: ['./property-update.component.css']
})
export class PropertyUpdateComponent implements OnInit {

    propertyId: number;
    property: Property;
    errors: string[];

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private propertyService: PropertyService) {
            this.property = new Property();
        }

    ngOnInit() {
        this.propertyId = this.route.snapshot.params['id'];

        this.propertyService.findOneProperty(this.propertyId)
            .subscribe(data => {this.property = data});
    }

    onSubmit() {
        this.propertyService.update(this.propertyId, this.property)
            .subscribe(
                result => {
                    if (Array.isArray(result)) {
                        this.errors = result;
                    } else {
                        this.gotoPropertiesList();
                    }
                })

    }

    gotoPropertiesList() {
        this.router.navigate(['/admin/properties']);
    }
}
