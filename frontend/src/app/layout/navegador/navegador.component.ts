import { Component, OnInit } from '@angular/core';
import * as $ from "jquery";
import { Usuario } from 'src/app/models/usuario';

@Component({
  selector: 'app-navegador',
  templateUrl: './navegador.component.html',
  styleUrls: ['./navegador.component.css']
})
export class NavegadorComponent implements OnInit {

  logeado: boolean;
  idusuario: string;

  constructor() { }

  ngOnInit(): void {
    $('.btn-expand-collapse').click(function() {
      $('.navbar-primary').toggleClass('collapsed');
      $('.glyphicon-menu-left').toggleClass('icono-caretLeft');
      $('.glyphicon-menu-left').toggleClass('icono-caretRight');
      $('.contenido').toggleClass('col-10');
      $('.contenido').toggleClass('position-relative');
      $('.desplegable').toggleClass('col-1');
    });

    this.logeado = false;

    if(sessionStorage.getItem("usuarioIDgamepoint") != null) {
      this.idusuario = sessionStorage.getItem("usuarioIDgamepoint");
      this.logeado = true;
    }
  }

}
