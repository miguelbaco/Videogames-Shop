import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {APP_BASE_HREF} from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import * as $ from "jquery";

import { AppComponent } from './app.component';
import { MenuComponent } from './layout/menu/menu.component';
import { BuscadorComponent } from './layout/menu/buscador/buscador.component';
import { LoginComponent } from './layout/menu/login/login.component';
import { NavegadorComponent } from './layout/navegador/navegador.component';
import { JuegoComponent } from './contenido/juego/juego.component';
import { ValoracionComponent } from './contenido/juego/valoracion/valoracion.component';
import { ListarjuegosComponent } from './contenido/listarjuegos/listarjuegos.component';
import { CarritoComponent } from './contenido/carrito/carrito.component';
import { DeseosComponent } from './contenido/deseos/deseos.component';
import { ComprasComponent } from './contenido/compras/compras.component';
import { AppRoutingModule } from './app-routing.module';
import { InicioComponent } from './contenido/inicio/inicio.component';
import { NovedadesComponent } from './contenido/novedades/novedades.component';
import { ListarcategoriasComponent } from './contenido/listarcategorias/listarcategorias.component';
import { CategoriaComponent } from './contenido/categoria/categoria.component';
import { UsuarioComponent } from './contenido/usuario/usuario.component';
import { ErrorpageComponent } from './layout/errorpage/errorpage.component';
import { JuegosadminComponent } from './admin/contenido/juegosadmin/juegosadmin.component';
import { NavegadoradminComponent } from './admin/layout/navegadoradmin/navegadoradmin.component';
import { MenuadminComponent } from './admin/layout/menuadmin/menuadmin.component';
import { AdminComponent } from './admin/admin.component';
import { CategoriasadminComponent } from './admin/contenido/categoriasadmin/categoriasadmin.component';
import { UsuariosadminComponent } from './admin/contenido/usuariosadmin/usuariosadmin.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    BuscadorComponent,
    LoginComponent,
    NavegadorComponent,
    JuegoComponent,
    ValoracionComponent,
    ListarjuegosComponent,
    CarritoComponent,
    DeseosComponent,
    ComprasComponent,
    InicioComponent,
    NovedadesComponent,
    ListarcategoriasComponent,
    CategoriaComponent,
    UsuarioComponent,
    ErrorpageComponent,
    JuegosadminComponent,
    NavegadoradminComponent,
    MenuadminComponent,
    AdminComponent,
    CategoriasadminComponent,
    UsuariosadminComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule
  ],
  providers: [{provide: APP_BASE_HREF, useValue : '/' }],
  bootstrap: [AppComponent]
})
export class AppModule { }
