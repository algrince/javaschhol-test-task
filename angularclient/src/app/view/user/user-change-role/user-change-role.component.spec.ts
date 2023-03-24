import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserChangeRoleComponent } from './user-change-role.component';

describe('UserChangeRoleComponent', () => {
  let component: UserChangeRoleComponent;
  let fixture: ComponentFixture<UserChangeRoleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserChangeRoleComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserChangeRoleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
