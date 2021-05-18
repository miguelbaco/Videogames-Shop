import { Component, OnInit } from '@angular/core';
import { JuegosService } from '../../services/juegos.service';
import { Error } from '../../models/error';
import { Producto } from '../../models/producto';
import { DatosService } from 'src/app/services/datos.service';
import { CategoriasService } from 'src/app/services/categorias.service';

@Component({
  selector: 'app-novedades',
  templateUrl: './novedades.component.html',
  styleUrls: ['./novedades.component.css']
})
export class NovedadesComponent implements OnInit {

  error: Error;
  listajuegos: Producto[] = []
  listajuegosfinal: Producto[] = [];
  public nuevojuego: Producto;

  constructor(private juegosService: JuegosService, private datosService: DatosService, private categoriasService: CategoriasService) { }

  ngOnInit(): void {
    this.mostrarjuegos();
    if(this.listajuegos != null) {
      for(let juego of this.listajuegos) {
        this.listajuegosfinal.unshift(juego);
      }
    }
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
 
      }, () => {
 
      }
    );
  }

  ordenarjuegos() {
    let numerorandom = this.getRandomValue(0, this.listajuegos.length);
    const productorandom = this.listajuegos[numerorandom];
    this.listajuegosfinal.push(productorandom);
    this.listajuegos.splice(numerorandom, 1);
    if (this.listajuegos.length === 1) {
      this.listajuegosfinal.push(productorandom);
    } else {
      this.ordenarjuegos();
    }
  }

  getRandomValue(min, max) { // min and max included 
    return Math.floor(Math.random() * (max - min + 1) + min);
  }

  categorias() {
    if(this.datosService.categorias == undefined) {
      this.categoriasService.allCategorias().subscribe(
        (response) => {
          this.datosService.categorias = response.data;
        }, (error) => {
        }, () => {

        }
      );
    }
  }

}
