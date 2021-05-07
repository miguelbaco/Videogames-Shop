import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CarritoComponent } from './contenido/carrito/carrito.component';
import { DeseosComponent } from './contenido/deseos/deseos.component';
import { ComprasComponent } from './contenido/compras/compras.component';
import { ListarjuegosComponent } from './contenido/listarjuegos/listarjuegos.component';
import { JuegoComponent } from './contenido/juego/juego.component';
import { NovedadesComponent } from './contenido/novedades/novedades.component';
import { ListarcategoriasComponent } from './contenido/listarcategorias/listarcategorias.component';
import { CategoriaComponent } from './contenido/categoria/categoria.component';
import { BusquedaComponent } from './contenido/busqueda/busqueda.component';
import { UsuarioComponent } from './contenido/usuario/usuario.component';
import { InicioComponent } from './contenido/inicio/inicio.component';
import { ErrorpageComponent } from './layout/errorpage/errorpage.component';
import { JuegosadminComponent } from './admin/contenido/juegosadmin/juegosadmin.component';
import { CategoriasadminComponent } from './admin/contenido/categoriasadmin/categoriasadmin.component';
import { UsuariosadminComponent } from './admin/contenido/usuariosadmin/usuariosadmin.component';


const routes: Routes = [
  {path : 'carrito', component: CarritoComponent},
  {path : 'lista-de-deseos', component: DeseosComponent},
  {path : 'juegos', component: ListarjuegosComponent},
  {path : 'juego', component: JuegoComponent},
  {path : 'compras', component: ComprasComponent},
  {path : 'novedades', component: NovedadesComponent},
  {path : 'categorias', component: ListarcategoriasComponent},
  {path : 'categoria', component: CategoriaComponent},
  {path : 'busqueda', component: BusquedaComponent},
  {path : 'usuario', component: UsuarioComponent},
  {path : 'notfound', component: ErrorpageComponent},

  {path : 'adminjuegos', component: JuegosadminComponent},
  {path : 'admincategorias', component: CategoriasadminComponent},
  {path : 'adminusuarios', component: UsuariosadminComponent},

  {path: '', component: InicioComponent},
  {path : '**', component: ErrorpageComponent, data: { estado: 404 }, pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
