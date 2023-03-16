import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PropertyValueUpdateComponent } from './property-value-update.component';

describe('PropertyValueUpdateComponent', () => {
  let component: PropertyValueUpdateComponent;
  let fixture: ComponentFixture<PropertyValueUpdateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PropertyValueUpdateComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PropertyValueUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
