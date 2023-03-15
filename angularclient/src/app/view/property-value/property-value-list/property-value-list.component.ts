import { Component, OnInit } from '@angular/core';
import { PropertyValue } from '../../../model/property-value';
import { PropertyValueService } from '../../../service/property-value.service';

@Component({
  selector: 'app-property-value-list',
  templateUrl: './property-value-list.component.html',
  styleUrls: ['./property-value-list.component.css']
})
export class PropertyValueListComponent implements OnInit {

    propertyValues: PropertyValue[];

    constructor(private propertyValueService: PropertyValueService) {}

    ngOnInit() {
        this.propertyValueService.findAll()
            .subscribe(data => {this.propertyValues = data})
    }

}
