import { Component } from '@angular/core';
import { Property } from '../model/property';
import { PropertyService } from '../service/property.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-property-create',
  templateUrl: './property-create.component.html',
  styleUrls: ['./property-create.component.css']
})
export class PropertyCreateComponent {

    property: Property;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private propertyService: PropertyService) {
            this.property = new Property();
        }

    onSubmit() {
        this.propertyService.save(this.property)
            .subscribe(result => this.gotoPropertiesList());
    }

    gotoPropertiesList() {
        this.router.navigate(['/properties'])
    }
}
