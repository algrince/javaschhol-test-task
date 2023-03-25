import { Component, OnInit } from '@angular/core';
import { Property } from '../../../model/property';
import { PropertyService } from '../../../service/property.service';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-property-list',
  templateUrl: './property-list.component.html',
  styleUrls: ['./property-list.component.css']
})
export class PropertyListComponent implements OnInit {

    properties: Property[];
    role: string;

    constructor(
        private propertyService: PropertyService,
        private cookieService: CookieService) {}

    ngOnInit() {
        this.propertyService.findAll()
            .subscribe(data => {this.properties = data});

        this.role = this.cookieService.get("userRole");
    }

}
