import { Component, OnInit, Provider } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Producto } from 'src/app/models/producto';
import { DeseosService } from 'src/app/services/deseos.service';
import { JuegosService } from '../../services/juegos.service';

@Component({
  selector: 'app-juego',
  templateUrl: './juego.component.html',
  styleUrls: ['./juego.component.css']
})
export class JuegoComponent implements OnInit {

  juego : Producto;
  juegoboolean: boolean;

  constructor(private ruta: ActivatedRoute, private juegosService: JuegosService, private deseoService: DeseosService) { }

  ngOnInit(): void {
    this.juegoboolean = false;
    this.mostrarjuego();
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
          this.juegoboolean = true;
      }, (error) => {
 
      }, () => {
 
      }
    );
  }

  anadirdeseo() {
    if(sessionStorage.getItem("usuarioIDgamepoint") != null) {
      let idusuario = +sessionStorage.getItem("usuarioIDgamepoint");
      this.deseoService.anadirDeseo(idusuario, this.ruta.snapshot.params.id).subscribe();
    }
  }

}
