import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PropertyValueListComponent } from './property-value-list.component';

describe('PropertyValueListComponent', () => {
  let component: PropertyValueListComponent;
  let fixture: ComponentFixture<PropertyValueListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PropertyValueListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PropertyValueListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
