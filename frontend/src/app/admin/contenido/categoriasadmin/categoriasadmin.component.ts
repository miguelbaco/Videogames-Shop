import { Component, OnInit } from '@angular/core';
import { Categoria } from 'src/app/models/categoria';
import { AdminService } from 'src/app/services/admin.service';
import { CategoriasService } from 'src/app/services/categorias.service';
import { DatosService } from 'src/app/services/datos.service';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-categoriasadmin',
  templateUrl: './categoriasadmin.component.html',
  styleUrls: ['./categoriasadmin.component.css']
})
export class CategoriasadminComponent implements OnInit {

  listacategorias: Categoria[];

  nuevaCategoria: Categoria = new Categoria();

  categoriaModificar: Categoria = new Categoria();

  constructor(private adminService: AdminService, private datosService: DatosService, private categoriasService: CategoriasService) { }

  ngOnInit(): void {
    this.categorias();
  }

  categorias() {
    if(this.datosService.categorias == undefined) {
      this.categoriasService.allCategorias().subscribe(
        (response) => {
          this.datosService.categorias = response.data;
          this.listacategorias = response.data;
        }
      );
    } else {
      this.listacategorias = this.datosService.categorias;
    }
  }

  anadircategoria() {
    if(sessionStorage.getItem("usuarioIDgamepoint") != null) {
      let idusuario = +sessionStorage.getItem("usuarioIDgamepoint");
      if(this.nuevaCategoria.nombre != null && this.nuevaCategoria.descripcion != null) {
        this.adminService.anadirCategoria(this.nuevaCategoria, idusuario).subscribe();
        this.listacategorias.push(this.nuevaCategoria);
        this.nuevaCategoria = new Categoria();
      }
    }
  }

  categoriaamodificar(categoria: Categoria) {
    this.categoriaModificar = categoria;
  }

  modificarcategoria() {
    if(sessionStorage.getItem("usuarioIDgamepoint") != null) {
      let idusuario = +sessionStorage.getItem("usuarioIDgamepoint");
      if(this.categoriaModificar.nombre != null && this.categoriaModificar.descripcion != null) {
        this.adminService.modificarCategoria(this.categoriaModificar, idusuario).subscribe();
      }
    }
  }

}
