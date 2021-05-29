import { Component, OnInit } from '@angular/core';
import { JuegosService } from '../../services/juegos.service';
import { Error } from '../../models/error';
import { Producto } from '../../models/producto';
import { DatosService } from 'src/app/services/datos.service';
import { CategoriasService } from 'src/app/services/categorias.service';
import { ActivatedRoute, NavigationStart, Router } from '@angular/router';
import { Location } from "@angular/common";
import { Categoria } from 'src/app/models/categoria';

@Component({
  selector: 'app-categoria',
  templateUrl: './categoria.component.html',
  styleUrls: ['./categoria.component.css']
})
export class CategoriaComponent implements OnInit {

  listajuegos: Producto[] = []
  public nuevojuego: Producto;
  public categoria: Categoria;

  public notificarError: Error;
  public noHayJuegos: boolean;

  route: string;

  constructor(location: Location, router: Router, private ruta: ActivatedRoute, private juegosService: JuegosService, private datosService: DatosService, private categoriasService: CategoriasService) {
    router.events.subscribe(val => {
      if (val instanceof NavigationStart) {
        this.listajuegos = [];
        this.mostrarjuegos();
    }
    });
  }

  ngOnInit(): void {
    this.notificarError = new Error;
    this.noHayJuegos = false;
    if(this.listajuegos.length == 0) {
      this.listajuegos = [];
      this.mostrarjuegos();
    }
  }

  mostrarjuegos() {
    this.categorias();
    this.juegosService.juegosCategoria(this.ruta.snapshot.params.id).subscribe(
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

    this.categoria = this.datosService.categorias.find(x => x.id ==this.ruta.snapshot.params.id);
  }

}
