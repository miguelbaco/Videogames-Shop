import { Component, OnInit } from '@angular/core';
import { Usuario } from 'src/app/models/usuario';
import { CategoriasService } from 'src/app/services/categorias.service';
import { DatosService } from 'src/app/services/datos.service';
import { PedidosService } from 'src/app/services/pedidos.service';
import { Error } from '../../models/error';
import { DetallePedido } from 'src/app/models/detallePedido';

@Component({
  selector: 'app-compras',
  templateUrl: './compras.component.html',
  styleUrls: ['./compras.component.css']
})
export class ComprasComponent implements OnInit {

  constructor(private datosService: DatosService, private pedidosService: PedidosService, private categoriasService: CategoriasService) { }

  usuariolog: Usuario;

  // Variables de productos en camino
  pedidosCamino: DetallePedido[] = []
  public notificarErrorCamino: Error;
  public noHayJuegosCamino: boolean;

  // Variables de productos en fase de devolución
  pedidosDevolucion: DetallePedido[] = []
  public notificarErrorDevolucion: Error;
  public noHayJuegosDevolucion: boolean;

  // Variables de productos sin devolución
  pedidosSinDevolucion: DetallePedido[] = []
  public notificarErrorSinDevolucion: Error;
  public noHayJuegosSinDevolucion: boolean;

  ngOnInit(): void {
    this.notificarErrorCamino = new Error;
    this.noHayJuegosCamino = false;
    this.notificarErrorDevolucion = new Error;
    this.noHayJuegosDevolucion = false;
    this.notificarErrorSinDevolucion = new Error;
    this.noHayJuegosSinDevolucion = false;
    this.mostrarjuegosadquiridos();
  }

  mostrarjuegosadquiridos() {
    if(sessionStorage.getItem("usuarioIDgamepoint") != null) {
      this.categorias();
      let idusuario = +sessionStorage.getItem("usuarioIDgamepoint");
      this.pedidosService.comprasEnCamino(idusuario).subscribe(
        (response) => {
          this.pedidosCamino = response.data;
        }, (error) => {
          this.notificarErrorCamino = error.error.error[0];
          this.noHayJuegosCamino = true;
        }
      );

      this.pedidosService.comprasEnDevolucion(idusuario).subscribe(
        (response) => {
          this.pedidosDevolucion = response.data;
        }, (error) => {
          this.notificarErrorDevolucion = error.error.error[0];
          this.noHayJuegosDevolucion = true;
        }
      );

      this.pedidosService.comprasSinDevolucion(idusuario).subscribe(
        (response) => {
          this.pedidosSinDevolucion = response.data;
        }, (error) => {
          this.notificarErrorSinDevolucion = error.error.error[0];
          this.noHayJuegosSinDevolucion = true;
        }
      );
    }
  }

  devolverPedido(pedido: DetallePedido) { // setea como true el pedido en BD y se cambia en cliente 
    this.pedidosService.devolverDetalleProducto(pedido.pedido.id, pedido.producto.id).subscribe();
    pedido.devuelto = true;
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
