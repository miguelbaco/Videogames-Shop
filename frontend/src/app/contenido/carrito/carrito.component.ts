import { Component, OnInit } from '@angular/core';
import { CategoriasService } from 'src/app/services/categorias.service';
import { DatosService } from 'src/app/services/datos.service';
import { PedidosService } from 'src/app/services/pedidos.service';
import { Producto } from '../../models/producto';

@Component({
  selector: 'app-carrito',
  templateUrl: './carrito.component.html',
  styleUrls: ['./carrito.component.css']
})
export class CarritoComponent implements OnInit {

  constructor(private pedidosService: PedidosService, private datosService: DatosService, private categoriasService: CategoriasService) { }

  public nuevojuego: Producto;
  listajuegos: Producto[] = []
  preciocarrito: number;

  ngOnInit(): void {
   this.mostrarjuegos();
   this.preciocarrito = 0;
  }

  mostrarjuegos() {
    if(sessionStorage.getItem("usuarioIDgamepoint") != null) {
      this.categorias();
      let idusuario = +sessionStorage.getItem("usuarioIDgamepoint");
      this.pedidosService.carritoActual(idusuario).subscribe(
        (response) => {
          for(let juego of response.data) {
            let anadido: boolean = true;
            if(this.listajuegos.length != 0) {
              for(var juegolista of this.listajuegos) {
                if(juegolista.id == juego.id) {
                  juegolista.cantidad += 1;
                  anadido = false;
                }
              }
            }
            if(anadido) {
              this.nuevojuego = new Producto;
              this.nuevojuego.id = juego.id;
              this.nuevojuego.nombre = juego.nombre;
              this.nuevojuego.descripcion = juego.descripcion;
              this.nuevojuego.precio = juego.precio;
              this.nuevojuego.imagen = juego.imagen;
              this.nuevojuego.idcategoria = juego.idcategoria;
              this.nuevojuego.nombrecategoria = this.datosService.categorias.find(x => x.id == this.nuevojuego.idcategoria).nombre;
              this.nuevojuego.cantidad = 1;
              this.listajuegos.push(this.nuevojuego);
            }
          }
          this.preciototal();
        }, (error) => {
        }, () => {
  
        }
      );
    }
  }

  eliminarcarrito(idjuego: number) {
    let idusuario = +sessionStorage.getItem("usuarioIDgamepoint");
    this.pedidosService.eliminarCarrito(idusuario, idjuego).subscribe(
      (response) => {
        this.listajuegos = [];
        this.mostrarjuegos();
        this.preciototal();
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

  preciototal() {
    this.preciocarrito = 0;
    if(this.listajuegos.length != 0) {
      for(var juegolista of this.listajuegos) {
        this.preciocarrito += (juegolista.precio * juegolista.cantidad);
      }
    }
  }

}
