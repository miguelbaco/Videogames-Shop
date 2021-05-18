import { Component, OnInit } from '@angular/core';
import { event } from 'jquery';
import { CategoriasService } from 'src/app/services/categorias.service';
import { DatosService } from 'src/app/services/datos.service';
import { DeseosService } from 'src/app/services/deseos.service';
import { JuegosService } from 'src/app/services/juegos.service';
import { Producto } from '../../models/producto';

@Component({
  selector: 'app-deseos',
  templateUrl: './deseos.component.html',
  styleUrls: ['./deseos.component.css']
})
export class DeseosComponent implements OnInit {

  public nuevojuego: Producto;
  listajuegos: Producto[] = []

  constructor(private deseosService: DeseosService, private datosService: DatosService, private categoriasService: CategoriasService) { }

  ngOnInit(): void {
    this.mostrarjuegos();
  }

  mostrarjuegos() {
    if(sessionStorage.getItem("usuarioIDgamepoint") != null) {
      this.categorias();
      let idusuario = +sessionStorage.getItem("usuarioIDgamepoint");
      this.deseosService.DeseosUsuario(idusuario).subscribe(
        (response) => {
          for(let juego of response.data) {
            this.nuevojuego = new Producto;
            this.nuevojuego.id = juego.producto.id;
            this.nuevojuego.nombre = juego.producto.nombre;
            this.nuevojuego.precio = juego.producto.precio;
            this.nuevojuego.imagen = juego.producto.imagen;
            this.nuevojuego.idcategoria = juego.producto.idcategoria;
            this.nuevojuego.nombrecategoria = this.datosService.categorias.find(x => x.id == this.nuevojuego.idcategoria).nombre;
            this.listajuegos.push(this.nuevojuego);
          }
        }, (error) => {
        }, () => {
  
        }
      );
    }
  }

  eliminardeseo(idjuego: number) {
    let idusuario = +sessionStorage.getItem("usuarioIDgamepoint");
    this.deseosService.eliminarDeseo(idusuario, idjuego).subscribe(
      (response) => {
        this.listajuegos = [];
        this.mostrarjuegos();
      }, (error) => {
      }, () => {

      }
    );
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
