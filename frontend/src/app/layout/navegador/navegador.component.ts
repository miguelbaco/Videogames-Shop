import { Component, OnInit } from '@angular/core';
import * as $ from "jquery";

@Component({
  selector: 'app-navegador',
  templateUrl: './navegador.component.html',
  styleUrls: ['./navegador.component.css']
})
export class NavegadorComponent implements OnInit {

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
  }

}
