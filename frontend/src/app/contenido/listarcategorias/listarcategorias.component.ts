import { Component, OnInit } from '@angular/core';
import { Categoria } from 'src/app/models/categoria';
import { CategoriasService } from 'src/app/services/categorias.service';
import { DatosService } from 'src/app/services/datos.service';
import { Error } from '../../models/error';

@Component({
  selector: 'app-listarcategorias',
  templateUrl: './listarcategorias.component.html',
  styleUrls: ['./listarcategorias.component.css']
})
export class ListarcategoriasComponent implements OnInit {

  listacategorias: Categoria[];

  public notificarError: Error;
  public noHayCategorias: boolean;

  constructor(private datosService: DatosService, private categoriasService: CategoriasService) { }

  ngOnInit(): void {
    this.notificarError = new Error;
    this.noHayCategorias = false;
    this.categorias();
  }

  categorias() {
    if(this.datosService.categorias == undefined) {
      this.categoriasService.allCategorias().subscribe(
        (response) => {
          this.datosService.categorias = response.data;
          this.listacategorias = response.data;
        }, (error) => {
          this.notificarError = error.error.error[0];
          this.noHayCategorias = true;
        }
      );
    } else {
      this.listacategorias = this.datosService.categorias;
    }
  }

}
