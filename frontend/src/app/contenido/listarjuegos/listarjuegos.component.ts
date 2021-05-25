import { Component, OnInit } from '@angular/core';
import { JuegosService } from '../../services/juegos.service';
import { Error } from '../../models/error';
import { Producto } from '../../models/producto';
import { DatosService } from 'src/app/services/datos.service';
import { CategoriasService } from 'src/app/services/categorias.service';

@Component({
  selector: 'app-listarjuegos',
  templateUrl: './listarjuegos.component.html',
  styleUrls: ['./listarjuegos.component.css']
})
export class ListarjuegosComponent implements OnInit {

  error: Error;
  listajuegos: Producto[] = []
  public nuevojuego: Producto;

  public notificarError: Error;
  public noHayJuegos: boolean;

  constructor(private juegosService: JuegosService, private datosService: DatosService, private categoriasService: CategoriasService) { }

  ngOnInit(): void {
    this.notificarError = new Error;
    this.noHayJuegos = false;
    this.mostrarjuegos();
  }

  mostrarjuegos() {
    this.categorias();
    this.juegosService.allJuegos().subscribe(
      (response) => {
        for(let juego of response.data) {
          this.nuevojuego = new Producto;
          this.nuevojuego.id = juego.id;
          this.nuevojuego.nombre = juego.nombre;
          this.nuevojuego.precio = juego.precio;
          this.nuevojuego.imagen = juego.imagen;
          this.nuevojuego.idcategoria = juego.idcategoria;
          this.nuevojuego.nombrecategoria = this.datosService.categorias.find(x => x.id == this.nuevojuego.idcategoria).nombre;
          this.listajuegos.push(this.nuevojuego);
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
