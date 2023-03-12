import { Component, OnInit } from '@angular/core';
import { Property } from '../model/property';
import { PropertyService } from '../service/property.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-property-delete',
  templateUrl: './property-delete.component.html',
  styleUrls: ['./property-delete.component.css']
})
export class PropertyDeleteComponent implements OnInit {

    propertyId: number;
    property: Property;

    constructor(
        private propertyService: PropertyService,
        private router: Router,
        private route: ActivatedRoute) {
            this.property = new Property();
        }

    ngOnInit() {
        this.propertyId = this.route.snapshot.params['id'];

        this.propertyService.findOneProperty(this.propertyId)
            .subscribe(data => {this.property = data});
    }

    onSubmit() {
        this.propertyService.delete(this.propertyId)
            .subscribe(result => this.gotoPropertiesList());
    }

    gotoPropertiesList() {
        this.router.navigate(['/properties']);
    }

}
