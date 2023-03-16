import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { PropertyValue } from '../../../model/property-value';
import { PropertyValueService } from '../../../service/property-value.service';

@Component({
  selector: 'app-property-value-delete',
  templateUrl: './property-value-delete.component.html',
  styleUrls: ['./property-value-delete.component.css']
})
export class PropertyValueDeleteComponent implements OnInit {

    propertyValueId: number;
    propertyValue: PropertyValue;

    constructor(
        private propertyValueService: PropertyValueService,
        private router: Router,
        private route: ActivatedRoute) {
            this.propertyValue = new PropertyValue();
        }

    ngOnInit() {
        this.propertyValueId = this.route.snapshot.params['id'];

        this.propertyValueService.findOnePropertyValue(this.propertyValueId)
            .subscribe(data => {this.propertyValue = data});
    }

    onSubmit() {
        this.propertyValueService.delete(this.propertyValueId)
            .subscribe(result => this.gotoPropertyValues());
    }

    gotoPropertyValues() {
        this.router.navigate(['/propertyValues']);
    }
}
