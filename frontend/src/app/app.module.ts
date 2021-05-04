import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { MenuComponent } from './layout/menu/menu.component';
import { BuscadorComponent } from './layout/menu/buscador/buscador.component';
import { LoginComponent } from './layout/menu/login/login.component';
import { NavegadorComponent } from './layout/navegador/navegador.component';
import { JuegoComponent } from './contenido/juego/juego.component';
import { ValoracionComponent } from './contenido/juego/valoracion/valoracion.component';
import { ListarjuegosComponent } from './contenido/listarjuegos/listarjuegos.component';
import { CarritoComponent } from './contenido/carrito/carrito.component';

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
    CarritoComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
