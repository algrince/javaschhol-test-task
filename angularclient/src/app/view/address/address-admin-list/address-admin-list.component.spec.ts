import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddressAdminListComponent } from './address-admin-list.component';

describe('AddressAdminListComponent', () => {
  let component: AddressAdminListComponent;
  let fixture: ComponentFixture<AddressAdminListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddressAdminListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddressAdminListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
