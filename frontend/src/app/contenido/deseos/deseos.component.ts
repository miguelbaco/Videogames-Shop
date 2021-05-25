import { Component, OnInit } from '@angular/core';
import { CategoriasService } from 'src/app/services/categorias.service';
import { DatosService } from 'src/app/services/datos.service';
import { DeseosService } from 'src/app/services/deseos.service';
import { PedidosService } from 'src/app/services/pedidos.service';
import { Producto } from '../../models/producto';
import { Error } from '../../models/error';

@Component({
  selector: 'app-deseos',
  templateUrl: './deseos.component.html',
  styleUrls: ['./deseos.component.css']
})
export class DeseosComponent implements OnInit {

  public nuevojuego: Producto;
  listajuegos: Producto[] = []
  notificarPopoverCarrito: string;

  public notificarError: Error;
  public noHayJuegos: boolean;

  constructor(private pedidosService: PedidosService, private deseosService: DeseosService, private datosService: DatosService, private categoriasService: CategoriasService) { }

  ngOnInit(): void {
    this.notificarError = new Error;
    this.noHayJuegos = false;
    this.mostrarjuegos();
    this.notificarPopoverCarrito = "Añadido al carrito";
  }

  mostrarjuegos() {
    if(sessionStorage.getItem("usuarioIDgamepoint") != null) {
      this.categorias();
      let idusuario = +sessionStorage.getItem("usuarioIDgamepoint");
      this.deseosService.deseosUsuario(idusuario).subscribe(
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
          this.notificarError = error.error.error[0];
          this.noHayJuegos = true;
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
      }
    );
  }

  anadirCarrito(idjuego: number) {
    if(sessionStorage.getItem("usuarioIDgamepoint") != null) {
      let idusuario = +sessionStorage.getItem("usuarioIDgamepoint");
      this.pedidosService.anadirCarrito(idusuario, idjuego).subscribe(
        (response) => {
          if(response.data != null) {
            this.notificarPopoverCarrito = "Añadido al carrito";
          }
        }, (error) => {
          //this.notificarError = error.error.error[0];
          //this.notificarPopoverCarrito = this.notificarError.title;
        }, () => {
   
        }
      );
    }
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
