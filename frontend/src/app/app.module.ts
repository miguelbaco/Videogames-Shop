import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {APP_BASE_HREF} from '@angular/common';
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
import { BusquedaComponent } from './contenido/busqueda/busqueda.component';
import { ListarcategoriasComponent } from './contenido/listarcategorias/listarcategorias.component';
import { CategoriaComponent } from './contenido/categoria/categoria.component';
import { UsuarioComponent } from './contenido/usuario/usuario.component';
import { ErrorpageComponent } from './layout/errorpage/errorpage.component';

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
    BusquedaComponent,
    ListarcategoriasComponent,
    CategoriaComponent,
    UsuarioComponent,
    ErrorpageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [{provide: APP_BASE_HREF, useValue : '/' }],
  bootstrap: [AppComponent]
})
export class AppModule { }
