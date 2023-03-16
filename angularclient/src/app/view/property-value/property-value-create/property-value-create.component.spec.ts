import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PropertyValueCreateComponent } from './property-value-create.component';

describe('PropertyValueCreateComponent', () => {
  let component: PropertyValueCreateComponent;
  let fixture: ComponentFixture<PropertyValueCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PropertyValueCreateComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PropertyValueCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
