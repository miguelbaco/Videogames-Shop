import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavegadoradminComponent } from './navegadoradmin.component';

describe('NavegadoradminComponent', () => {
  let component: NavegadoradminComponent;
  let fixture: ComponentFixture<NavegadoradminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NavegadoradminComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NavegadoradminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
