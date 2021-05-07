import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JuegosadminComponent } from './juegosadmin.component';

describe('JuegosadminComponent', () => {
  let component: JuegosadminComponent;
  let fixture: ComponentFixture<JuegosadminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JuegosadminComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(JuegosadminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
