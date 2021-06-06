import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import * as $ from "jquery";
import { Producto } from 'src/app/models/producto';
import { Usuario } from 'src/app/models/usuario';
import { Valoracion } from 'src/app/models/valoracion';
import { JuegosService } from 'src/app/services/juegos.service';
import { FormControl } from '@angular/forms';
import { Error } from 'src/app/models/error';
import { DatosService } from 'src/app/services/datos.service';

@Component({
  selector: 'app-valoracion',
  templateUrl: './valoracion.component.html',
  styleUrls: ['./valoracion.component.css']
})
export class ValoracionComponent implements OnInit {

  valoraciones: Valoracion[] = [];
  nuevaValoracion: Valoracion;
  valoracionUsuario: Valoracion = new Valoracion;
  notaDelUsuario: number = null;

  comentarioUsuario = new FormControl('');

  public notificarErrorNuevaVal: Error = new Error;
  public errorNuevaVal : boolean = false;

  public notificarErrorValoraciones: Error = new Error;
  public errorValoraciones : boolean = false;

  constructor(private juegosService: JuegosService, private ruta: ActivatedRoute, private datosService: DatosService) { }

  ngOnInit(): void {
    this.valoracionesDelJuego();

    // Eventos mouseOver y mouseOut para colorear las estrellas y las anteriores a la que se tiene encima
    $(document).ready(function(){
  
      /* 1. Visualizing things on Hover - See next part for action on click */
      $('#stars li').on('mouseover', function(){
        var onStar = parseInt($(this).data('value'), 10); // The star currently mouse on
       
        // Now highlight all the stars that's not after the current hovered star
        $(this).parent().children('li.star').each(function(e){
          if (e < onStar) {
            $(this).addClass('hover');
          }
          else {
            $(this).removeClass('hover');
          }
        });
        
      }).on('mouseout', function(){
        $(this).parent().children('li.star').each(function(e){
          $(this).removeClass('hover');
        });
      });
      

      // Evento de click para dejar seleccionada las estrellas hasta donde se ha clickado   
      /* 2. Action to perform on click */
      $('#stars li').on('click', function(){
        var onStar = parseInt($(this).data('value'), 10); // The star currently selected
        var stars = $(this).parent().children('li.star');
        
        for (let i = 0; i < stars.length; i++) {
          $(stars[i]).removeClass('selected');
        }
        
        for (let i = 0; i < onStar; i++) {
          $(stars[i]).addClass('selected');
        }
        
      });      
    });

  }

  valoracionesDelJuego() {
    this.valoraciones = [];
    this.juegosService.valoracionesJuego(this.ruta.snapshot.params.id).subscribe(
      (response) => {
        for(let valoracion of response.data) {
          this.nuevaValoracion = new Valoracion;
          this.nuevaValoracion.usuario = valoracion.usuario;
          this.nuevaValoracion.producto = valoracion.producto;
          this.nuevaValoracion.comentario = valoracion.comentario;
          this.nuevaValoracion.puntuacion = valoracion.puntuacion;
          this.valoraciones.push(this.nuevaValoracion);
        }
      }, (error) => {
        this.notificarErrorValoraciones = error.error.error[0];
          this.errorValoraciones = true;
      }
    );
  }

  // Una vez recogida todas las valoraciones y guardadas por ngOnInit
  // empieza este método que recorre las valoraciones y le setea la puntuación
  ngAfterViewChecked() {
    let index = 0;
    let sumaValoraciones = 0;
    for(let valoracion of this.valoraciones) {
      let selector = "#stars" + index + " li";
        var onStar = valoracion.puntuacion;
        var stars = $(selector).parent().children('li.star');
        
        for (let i = 0; i < stars.length; i++) {
          $(stars[i]).removeClass('selected');
        }
        
        for (let i = 0; i < onStar; i++) {
          $(stars[i]).addClass('selected');
        }
      sumaValoraciones += valoracion.puntuacion;
      index++;
    }
    this.calculaNotaJuego(sumaValoraciones, index);
  }

  // Calcula la media y como se puntua sobre 5 y quiero la nota sobre 10 multiplico por 2
  calculaNotaJuego(suma: number, cantidad: number) {
    if(cantidad == 0) {
      this.datosService.valoracionfinal = null;
    } else {
      this.datosService.valoracionfinal = ((suma * 2) / cantidad);
    }
    this.datosService.numerovaloraciones = cantidad;
  }

  enviarValoracion() {
    if(sessionStorage.getItem("usuarioIDgamepoint") != null) {
      let idusuario = +sessionStorage.getItem("usuarioIDgamepoint");
      if(idusuario != null && this.notaDelUsuario != null && this.comentarioUsuario.value != undefined) {
        this.nuevaValoracion = new Valoracion;
        this.valoracionUsuario.usuario = new Usuario;
        this.valoracionUsuario.usuario.id = null;
        this.valoracionUsuario.producto = new Producto;
        this.valoracionUsuario.producto.id = null;
        this.valoracionUsuario.usuario.id = idusuario;
        this.valoracionUsuario.producto.id = this.ruta.snapshot.params.id;
        this.valoracionUsuario.puntuacion = this.notaDelUsuario;
        this.juegosService.anadirValoracion(this.valoracionUsuario).subscribe(
          (response) => {
            this.valoracionUsuario = response.data;
            this.valoraciones.push(this.valoracionUsuario);
            this.datosService.numerovaloraciones += 1;
            // Se resetea el menu de valorar y se restablecen las estrellas quitandole la class selected
            this.valoracionUsuario = new Valoracion;
            this.errorNuevaVal = false;
            this.comentarioUsuario.reset();
            let stars = $("#stars li").parent().children('li.star');
            for (let i = 0; i < stars.length; i++) {
              $(stars[i]).removeClass('selected');
            }
            this.errorValoraciones = false;
            this.ngAfterViewChecked();
          }, (error) => {
            this.notificarErrorNuevaVal = error.error.error[0];
            this.errorNuevaVal = true;
          }
        );
      } else { // Recoge errores
        if(idusuario == null) {
          this.notificarErrorNuevaVal.title = "No estas registrado para poder valorar este juego";
        } else if(this.notaDelUsuario == null) {
          this.notificarErrorNuevaVal.title = "No has seleccionado la puntuación que le vas a dar al juego";
        } else if(this.comentarioUsuario.value == undefined) {
          this.notificarErrorNuevaVal.title = "No has escrito una valoracion del juego";
        }
        this.errorNuevaVal = true;
      }
    } else {
      this.notificarErrorNuevaVal.title = "Inicia sesión o registrate para poder valorar el producto";
      this.errorNuevaVal = true;
    }
  }

  valoracion(nota: number) { // Asi se recoge la nota con un evento de click sobre la estrella clickada que guarda una nota
    this.notaDelUsuario = nota;
  }

}
