import { Component, OnInit } from '@angular/core';
import { Producto } from 'src/app/models/producto';
import { JuegosService } from 'src/app/services/juegos.service';
import { DatosService } from 'src/app/services/datos.service';
import { CategoriasService } from 'src/app/services/categorias.service';
import { AdminService } from 'src/app/services/admin.service';
import { Categoria } from 'src/app/models/categoria';
import { ThisReceiver } from '@angular/compiler';


@Component({
  selector: 'app-juegosadmin',
  templateUrl: './juegosadmin.component.html',
  styleUrls: ['./juegosadmin.component.css']
})
export class JuegosadminComponent implements OnInit {

  listacategorias: Categoria[];
  nuevaCategoria: Categoria;
  listajuegos: Producto[] = []
  public nuevojuego: Producto;

  productoNuevo: Producto = new Producto;
  fileToUpload: File = null;

  juegomodificar: Producto = new Producto;

  constructor(private adminService: AdminService, private datosService: DatosService, private categoriasService: CategoriasService) { }

  ngOnInit(): void {
    this.mostrarjuegos();
  }

  mostrarjuegos() {
    this.categorias(); 
    this.adminService.allJuegos().subscribe(
      (response) => {
        for(let juego of response.data) {
          this.nuevojuego = new Producto;
          this.nuevojuego =  juego;
          this.nuevojuego.nombrecategoria = this.datosService.categorias.find(x => x.id == this.nuevojuego.idcategoria).nombre;
          this.listajuegos.push(this.nuevojuego);
        }
      }
    );
    this.listacategorias
  }

  categorias() {
    if(this.datosService.categorias == undefined) {
      this.categoriasService.allCategorias().subscribe(
        (response) => {
          this.datosService.categorias = response.data;
          this.listacategorias = [];
          for(let categoria of response.data) {
            this.nuevaCategoria = new Categoria();
            this.nuevaCategoria = categoria;
            this.listacategorias.push(this.nuevaCategoria);
          }
        }
      );
    } else {
      this.listacategorias = this.datosService.categorias;
    }
  }

  eliminarJuego(producto: Producto) {
    if(sessionStorage.getItem("usuarioIDgamepoint") != null) {
      let idusuario = +sessionStorage.getItem("usuarioIDgamepoint");
      this.adminService.eliminarJuego(producto, idusuario).subscribe(
        () => {
          this.listajuegos = [];
          this.mostrarjuegos();
        }
      )
    }
  }

  anadirjuego() {
    if(sessionStorage.getItem("usuarioIDgamepoint") != null) {

      if(this.fileToUpload != null && this.productoNuevo.idcategoria != undefined && 
        this.productoNuevo.stock != null && this.productoNuevo.nombre != null && 
        this.productoNuevo.descripcion != null && this.productoNuevo.precio != null) {
          let idusuario = +sessionStorage.getItem("usuarioIDgamepoint");
          this.productoNuevo.imagen = this.fileToUpload.name;
          this.adminService.anadirJuego(this.productoNuevo, idusuario).subscribe();
          this.productoNuevo.nombrecategoria = this.datosService.categorias.find(x => x.id == this.nuevojuego.idcategoria).nombre;
          this.listajuegos.push(this.productoNuevo);
          this.adminService.subirImagen(this.fileToUpload).subscribe();
      }
    }
  }

  juegoamodificar(juego: Producto) {
    this.juegomodificar = juego;
  }

  modificarjuego() {
    if(sessionStorage.getItem("usuarioIDgamepoint") != null) {
          let idusuario = +sessionStorage.getItem("usuarioIDgamepoint");
          this.adminService.modificarJuego(this.juegomodificar, idusuario).subscribe();
    }
  }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }

}
