import { Component, OnInit } from '@angular/core';
import * as $ from "jquery";
import { Categoria } from 'src/app/models/categoria';
import { CategoriasService } from 'src/app/services/categorias.service';
import { DatosService } from 'src/app/services/datos.service';

@Component({
  selector: 'app-navegador',
  templateUrl: './navegador.component.html',
  styleUrls: ['./navegador.component.css']
})
export class NavegadorComponent implements OnInit {

  logeado: boolean;
  idusuario: string;
  categoriasboolean: boolean;
  categoriastotales: Categoria[];
  categoriasmenu: Categoria[];

  constructor(private datosService: DatosService, private categoriasService: CategoriasService) { }

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
    this.categoriasboolean = false;

    if(sessionStorage.getItem("usuarioIDgamepoint") != null) {
      this.idusuario = sessionStorage.getItem("usuarioIDgamepoint");
      this.logeado = true;
    }

    this.categoriastotales = [];
    this.categoriasmenu = [];
    this.categorias();
  }

  categorias() {
    if(this.datosService.categorias == undefined) {
      this.categoriasService.allCategorias().subscribe(
        (response) => {
          this.datosService.categorias = response.data;
          this.categoriastotales = response.data;
          this.insertarEnMenu();
        }, (error) => {
        }, () => {

        }
      );
    }
  }

  insertarEnMenu() {
    this.categoriasmenu.push(this.categoriastotales[Math.floor(Math.random() * (this.categoriastotales.length))]);
    let cantidadcategorias = 3;

    if(this.categoriastotales.length = 2) {
      cantidadcategorias = 2;
    } else if(this.categoriastotales.length = 1) {
      cantidadcategorias = 1;
    } else if(this.categoriastotales.length = 0) {
      cantidadcategorias = 0;
    }

    while(this.categoriasmenu.length != cantidadcategorias) {
      let categoria : Categoria;
      categoria = this.categoriastotales[Math.floor(Math.random() * (this.categoriastotales.length))];
      if(!this.categoriasmenu.includes(categoria)) {
        this.categoriasmenu.push(categoria);
      }
    }
    this.categoriasboolean = true;
  }

}
