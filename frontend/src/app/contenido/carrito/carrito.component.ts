import { Component, OnInit } from '@angular/core';
import { CategoriasService } from 'src/app/services/categorias.service';
import { DatosService } from 'src/app/services/datos.service';
import { PedidosService } from 'src/app/services/pedidos.service';
import { Producto } from '../../models/producto';
import { Error } from '../../models/error';

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

  public notificarError: Error;
  public noHayCarrito: boolean;

  ngOnInit(): void {
    this.notificarError = new Error;
    this.notificarError.title = "No tienes nada dentro del carrito";
    this.noHayCarrito = false;
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
              for(var juegolista of this.listajuegos) { //Se recorre la lista de juegos para añadir cantidad si se repite
                if(juegolista.id == juego.id) {
                  juegolista.cantidad += 1;
                  anadido = false;
                }
              }
            }
            if(anadido) { // Si el juego es nuevo porque no se ha recogido aun se hace push
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
          this.preciototal(); // Con todos los juegos se comprueba el precio total del carrito
        }, (error) => {
          this.notificarError = error.error.error[0];
          this.noHayCarrito = true;
        }
      );
    }
  }

  eliminarcarrito(idjuego: number) {
    let idusuario = +sessionStorage.getItem("usuarioIDgamepoint");
    this.pedidosService.eliminarCarrito(idusuario, idjuego).subscribe(
      (response) => {
        //Una vez eliminado, se resetea y se vuelve a llamar al backend
        this.listajuegos = [];
        this.mostrarjuegos();
        this.preciototal();
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

  preciototal() {
    this.preciocarrito = 0;
    if(this.listajuegos.length != 0) {
      for(var juegolista of this.listajuegos) {
        this.preciocarrito += (juegolista.precio * juegolista.cantidad);
      }
    }
  }

  realizarCompra() { // Realiza la compra y resetea listajuegos
    let idusuario = +sessionStorage.getItem("usuarioIDgamepoint");
    this.pedidosService.realizarCompra(idusuario).subscribe();
    this.listajuegos = [];
  }

}
