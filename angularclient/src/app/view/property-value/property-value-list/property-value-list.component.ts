import { Component, OnInit } from '@angular/core';
import { PropertyValue } from '../../../model/property-value';
import { PropertyValueService } from '../../../service/property-value.service';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-property-value-list',
  templateUrl: './property-value-list.component.html',
  styleUrls: ['./property-value-list.component.css']
})
export class PropertyValueListComponent implements OnInit {

    propertyValues: PropertyValue[];
    role: string;

    constructor(private propertyValueService: PropertyValueService,
    private cookieService: CookieService) {}

    ngOnInit() {
        this.propertyValueService.findAll()
            .subscribe(data => {this.propertyValues = data});

        this.role = this.cookieService.get("userRole");
    }

}
