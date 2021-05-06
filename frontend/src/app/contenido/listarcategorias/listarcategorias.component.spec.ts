import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListarcategoriasComponent } from './listarcategorias.component';

describe('ListarcategoriasComponent', () => {
  let component: ListarcategoriasComponent;
  let fixture: ComponentFixture<ListarcategoriasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListarcategoriasComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListarcategoriasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
