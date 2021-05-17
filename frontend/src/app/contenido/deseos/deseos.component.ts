import { Component, OnInit } from '@angular/core';
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

  constructor(private deseosService: DeseosService) { }

  ngOnInit(): void {
    this.mostrarjuegos();
  }

  mostrarjuegos() {
    if(sessionStorage.getItem("usuarioIDgamepoint") != null) {
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
            this.listajuegos.push(this.nuevojuego);
          }
        }, (error) => {
  
        }, () => {
  
        }
      );
    }
  }

}
