import { Component, OnInit } from '@angular/core';
import { Usuario } from 'src/app/models/usuario';
import { CategoriasService } from 'src/app/services/categorias.service';
import { DatosService } from 'src/app/services/datos.service';
import { PedidosService } from 'src/app/services/pedidos.service';
import { Producto } from '../../models/producto';
import { Error } from '../../models/error';

@Component({
  selector: 'app-compras',
  templateUrl: './compras.component.html',
  styleUrls: ['./compras.component.css']
})
export class ComprasComponent implements OnInit {

  constructor(private datosService: DatosService, private pedidosService: PedidosService, private categoriasService: CategoriasService) { }

  usuariolog: Usuario;

  public nuevojuego: Producto;
  listajuegos: Producto[] = []

  public notificarError: Error;
  public noHayJuegos: boolean;

  ngOnInit(): void {
    this.notificarError = new Error;
    this.noHayJuegos = false;
    this.usuariolog = this.datosService.usuariologeado;
    this.mostrarjuegosadquiridos();
  }

  mostrarjuegosadquiridos() {
    if(sessionStorage.getItem("usuarioIDgamepoint") != null) {
      this.categorias();
      let idusuario = +sessionStorage.getItem("usuarioIDgamepoint");
      this.pedidosService.juegosComprados(idusuario).subscribe(
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
              this.nuevojuego = juego;
              this.nuevojuego.nombrecategoria = this.datosService.categorias.find(x => x.id == this.nuevojuego.idcategoria).nombre;
              this.nuevojuego.cantidad = 1;
              this.listajuegos.push(this.nuevojuego);
            }
          }
        }, (error) => {
          this.notificarError = error.error.error[0];
          this.noHayJuegos = true;
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
        }, (error) => {
        }, () => {

        }
      );
    }
  }

}
