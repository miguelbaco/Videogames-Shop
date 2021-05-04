import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListarjuegosComponent } from './listarjuegos.component';

describe('ListarjuegosComponent', () => {
  let component: ListarjuegosComponent;
  let fixture: ComponentFixture<ListarjuegosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListarjuegosComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListarjuegosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
