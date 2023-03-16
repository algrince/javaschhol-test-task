import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PropertyValueDeleteComponent } from './property-value-delete.component';

describe('PropertyValueDeleteComponent', () => {
  let component: PropertyValueDeleteComponent;
  let fixture: ComponentFixture<PropertyValueDeleteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PropertyValueDeleteComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PropertyValueDeleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
