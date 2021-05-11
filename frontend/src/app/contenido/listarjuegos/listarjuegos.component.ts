import { Component, OnInit } from '@angular/core';
import { JuegosService } from '../../services/juegos.service';
import { Error } from '../../models/error';
import { Producto } from '../../models/producto';

@Component({
  selector: 'app-listarjuegos',
  templateUrl: './listarjuegos.component.html',
  styleUrls: ['./listarjuegos.component.css']
})
export class ListarjuegosComponent implements OnInit {

  error: Error;
  listajuegos: Producto[] = []
  public nuevojuego: Producto;

  constructor(private juegosService: JuegosService) { }

  ngOnInit(): void {
    this.mostrarjuegos();
  }

  mostrarjuegos() {
    this.juegosService.allJuegos().subscribe(
      (response) => {
        for(let juego of response.data) {
          this.nuevojuego = new Producto;
          this.nuevojuego.id = juego.id;
          this.nuevojuego.nombre = juego.nombre;
          this.nuevojuego.precio = juego.precio;
          this.nuevojuego.imagen = juego.imagen;
          this.nuevojuego.idcategoria = juego.idcategoria;
          this.listajuegos.push(this.nuevojuego);
        }
      }, (error) => {
 
      }, () => {
 
      }
    );
  }
}
