import { Component, OnInit } from '@angular/core';
import { Producto } from 'src/app/models/producto';
import { JuegosService } from 'src/app/services/juegos.service';
import { DatosService } from 'src/app/services/datos.service';
import { CategoriasService } from 'src/app/services/categorias.service';


@Component({
  selector: 'app-juegosadmin',
  templateUrl: './juegosadmin.component.html',
  styleUrls: ['./juegosadmin.component.css']
})
export class JuegosadminComponent implements OnInit {

  listajuegos: Producto[] = []
  public nuevojuego: Producto;

  constructor(private juegosService: JuegosService, private datosService: DatosService, private categoriasService: CategoriasService) { }

  ngOnInit(): void {
    this.mostrarjuegos();
  }

  mostrarjuegos() {
    this.categorias();
    this.juegosService.allJuegos().subscribe(
      (response) => {
        for(let juego of response.data) {
          this.nuevojuego = new Producto;
          this.nuevojuego =  juego;
          this.nuevojuego.nombrecategoria = this.datosService.categorias.find(x => x.id == this.nuevojuego.idcategoria).nombre;
          this.listajuegos.push(this.nuevojuego);
        }
      }
    );
  }

  categorias() {
    if(this.datosService.categorias == undefined) {
      this.categoriasService.allCategorias().subscribe(
        (response) => {
          this.datosService.categorias = response.data;
        }
      );
    }
  }

}
