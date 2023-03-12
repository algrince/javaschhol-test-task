import { Component, OnInit } from '@angular/core';
import { Property } from '../model/property';
import { PropertyService } from '../service/property.service';

@Component({
  selector: 'app-property-list',
  templateUrl: './property-list.component.html',
  styleUrls: ['./property-list.component.css']
})
export class PropertyListComponent implements OnInit {

    properties: Property[];

    constructor(private propertyService: PropertyService) {}

    ngOnInit() {
        this.propertyService.findAll()
            .subscribe(data => {this.properties = data});
    }

}
