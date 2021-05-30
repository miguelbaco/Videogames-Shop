import { Component, OnInit, Provider } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Producto } from 'src/app/models/producto';
import { DeseosService } from 'src/app/services/deseos.service';
import { JuegosService } from '../../services/juegos.service';
import { Error } from 'src/app/models/error';
import * as $ from "jquery";
import { DatosService } from 'src/app/services/datos.service';
import { PedidosService } from 'src/app/services/pedidos.service';

@Component({
  selector: 'app-juego',
  templateUrl: './juego.component.html',
  styleUrls: ['./juego.component.css']
})
export class JuegoComponent implements OnInit {

  juego : Producto;
  juegoboolean: boolean;

  public notificarError: Error;
  notificarPopoverDeseo: string;
  notificarPopoverCarrito: string;

  constructor(private ruta: ActivatedRoute, private datosService: DatosService, private juegosService: JuegosService, private deseoService: DeseosService, private pedidosService : PedidosService) { }

  ngOnInit(): void {
    this.juegoboolean = false;
    this.mostrarjuego();
    this.notificarError = new Error;
    this.notificarPopoverDeseo = "Añadido a tu lista de deseos";
    this.notificarPopoverCarrito = "Añadido al carrito";
  }

  mostrarjuego() {
    this.juegosService.juego(this.ruta.snapshot.params.id).subscribe(
      (response) => {
        this.juego = new Producto;
          this.juego.precio = response.data.precio;
          this.juego.imagen = response.data.imagen;
          this.juego.idcategoria = response.data.idcategoria;
          this.juego.descripcion = response.data.descripcion;
          this.juego.nombre = response.data.nombre;
          this.datosService.categorias.find(x => x.id == this.juego.idcategoria);
          this.juego.nombrecategoria = this.datosService.categorias.find(x => x.id == this.juego.idcategoria).nombre;
          this.juegoboolean = true;
      }
    );
  }

  anadirDeseo() {
    if(sessionStorage.getItem("usuarioIDgamepoint") != null) {
      let idusuario = +sessionStorage.getItem("usuarioIDgamepoint");
      this.deseoService.anadirDeseo(idusuario, this.ruta.snapshot.params.id).subscribe(
        (response) => {
          if(response.data != null) {
            this.notificarPopoverDeseo = "Añadido a tu lista de deseos";
          }
        }, (error) => {
          this.notificarError = error.error.error[0];
          this.notificarPopoverDeseo = this.notificarError.title;
        }, () => {
   
        }
      );
    } else {
      this.notificarPopoverDeseo = "Inicia sesión o registrate";
    }
  }

  anadirCarrito() {
    if(sessionStorage.getItem("usuarioIDgamepoint") != null) {
      let idusuario = +sessionStorage.getItem("usuarioIDgamepoint");
      this.pedidosService.anadirCarrito(idusuario, this.ruta.snapshot.params.id).subscribe(
        (response) => {
        }, (error) => {
          this.notificarError = error.error.error[0];
          this.notificarPopoverCarrito = this.notificarError.title;
        }
      );
    } else {
      this.notificarPopoverCarrito = "Inicia sesión o registrate";
    }
  }
}
