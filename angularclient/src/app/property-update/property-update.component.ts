import { Component, OnInit } from '@angular/core';
import { Property } from '../model/property';
import { PropertyService } from '../service/property.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-property-update',
  templateUrl: './property-update.component.html',
  styleUrls: ['./property-update.component.css']
})
export class PropertyUpdateComponent implements OnInit {

    propertyId: number;
    property: Property;

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
            .subscribe(result => this.gotoPropertiesList());
    }

    gotoPropertiesList() {
        this.router.navigate(['/properties']);
    }
}
