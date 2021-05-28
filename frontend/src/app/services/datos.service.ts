import { Injectable } from '@angular/core';
import { MenuadminComponent } from '../admin/layout/menuadmin/menuadmin.component';
import { NavegadorComponent } from '../layout/navegador/navegador.component';
import { Categoria } from '../models/categoria';
import { Usuario } from '../models/usuario';

@Injectable({
  providedIn: 'root'
})
export class DatosService {

  categorias: Categoria[];
  usuariologeado: Usuario;
  logeado: boolean = false;
  constructor() { }
  
}
