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
  indice: number = 0;
  numeroPaginas: number = 0;

  btnSiguiente: boolean = false;
  btnAtras: Boolean = false;
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
        this.ensenaJuegos();
        this.paginacion();
      }, (error) => {
        this.notificarError = error.error.error[0];
          this.noHayJuegos = true;
      }
    );
  }

  ensenaJuegos() {
    if(this.listajuegos != null) {
      this.listajuegosfinal = [];
      let indiceAnterior: number = this.indice;
      while(this.indice < (indiceAnterior + 6)) {
        this.listajuegosfinal.push(this.listajuegos[this.indice]);
        this.indice++;
      }
    }
  }

  cambiaEnsenaJuegos(nuevoindice: number) {
    this.listajuegosfinal = [];
    this.indice = nuevoindice;
    let indiceAnterior: number = this.indice;
    while(this.indice < (indiceAnterior + 6)) {
      this.listajuegosfinal.push(this.listajuegos[this.indice]);
      this.indice++;
    }
    if(this.indice >= this.listajuegos.length) {
      this.btnSiguiente = false;
    } else {
      this.btnSiguiente = true;
    }
    if(this.indice == 6) {
      this.btnAtras = false;
    } else {
      this.btnAtras = true;
    }
  }

  siguiente() {
    this.listajuegosfinal = [];
    let indiceAnterior: number = this.indice;
    while(this.indice < (indiceAnterior + 6)) {
      this.listajuegosfinal.push(this.listajuegos[this.indice]);
      this.indice++;
    }
    if(this.indice >= this.listajuegos.length) {
      this.btnSiguiente = false;
    }
    this.btnAtras = true;
  }

  atras() {
    this.listajuegosfinal = [];
    this.indice -= 12;
    let indiceAnterior: number = this.indice;
    while(this.indice < (indiceAnterior + 6)) {
      this.listajuegosfinal.push(this.listajuegos[this.indice]);
      this.indice++;
    }
    if(this.indice == 6) {
      this.btnAtras = false;
    }
    this.btnSiguiente = true;
  }

  paginacion() {
    this.numeroPaginas = Math.floor(this.listajuegos.length / 6);
    if(this.numeroPaginas > 1) {
      this.btnSiguiente = true;
    }
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
