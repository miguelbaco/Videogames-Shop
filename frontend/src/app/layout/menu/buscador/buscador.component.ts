import { Component, OnInit } from '@angular/core';
import { Producto } from 'src/app/models/producto';
import { CategoriasService } from 'src/app/services/categorias.service';
import { DatosService } from 'src/app/services/datos.service';
import { JuegosService } from 'src/app/services/juegos.service';

@Component({
  selector: 'app-buscador',
  templateUrl: './buscador.component.html',
  styleUrls: ['./buscador.component.css']
})
export class BuscadorComponent implements OnInit {

  strbusqueda: string;
  juegosbusqueda: Producto[] = [];

  public notificarError: Error;
  public noHayJuegos: boolean;
  public furula: boolean = true;

  constructor(private datosService: DatosService, private juegosService: JuegosService, private categoriasService: CategoriasService) {}

  ngOnInit(): void {
  }

  mostrarjuegos() {
    this.furula = false;
    this.juegosbusqueda = [];
    this.categorias();
    this.juegosService.buscarjuegos(this.strbusqueda).subscribe(
      (response) => {
        for(let juego of response.data) {
          let nuevojuego = new Producto;
          nuevojuego = juego;
          nuevojuego.nombrecategoria = this.datosService.categorias.find(x => x.id == nuevojuego.idcategoria).nombre;
          this.juegosbusqueda.push(nuevojuego);
        }
      }, (error) => {
        this.notificarError = error.error.error[0];
          this.noHayJuegos = true;
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
