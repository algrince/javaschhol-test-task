import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';
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
        private location: Location,
        private route: ActivatedRoute,
        private router: Router,
        private propertyService: PropertyService,
        private cookieService: CookieService) {}

    ngOnInit() {
        this.propertyService.findAll()
            .subscribe(data => {this.properties = data});

        this.role = this.cookieService.get("userRole");
    }

    restore(propertyId: number) {
        this.propertyService.restore(propertyId)
            .subscribe(result => location.reload());
    }

}
