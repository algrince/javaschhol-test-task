import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderUserListComponent } from './order-user-list.component';

describe('OrderUserListComponent', () => {
  let component: OrderUserListComponent;
  let fixture: ComponentFixture<OrderUserListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrderUserListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrderUserListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
