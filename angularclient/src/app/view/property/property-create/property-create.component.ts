import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Property } from '../../../model/property';
import { PropertyService } from '../../../service/property.service';

@Component({
  selector: 'app-property-create',
  templateUrl: './property-create.component.html',
  styleUrls: ['./property-create.component.css']
})
export class PropertyCreateComponent {

    property: Property;
    errors: string[];

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private propertyService: PropertyService) {
            this.property = new Property();
        }

    onSubmit() {
        this.propertyService.save(this.property)
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
        this.router.navigate(['/properties'])
    }
}
