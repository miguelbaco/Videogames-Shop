import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoriasadminComponent } from './categoriasadmin.component';

describe('CategoriasadminComponent', () => {
  let component: CategoriasadminComponent;
  let fixture: ComponentFixture<CategoriasadminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CategoriasadminComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CategoriasadminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
