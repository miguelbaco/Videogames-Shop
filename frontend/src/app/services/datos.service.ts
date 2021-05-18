import { Injectable } from '@angular/core';
import { Categoria } from '../models/categoria';
import { Usuario } from '../models/usuario';

@Injectable({
  providedIn: 'root'
})
export class DatosService {

  categorias: Categoria[];
  usuariologeado: Usuario;
  constructor() { }
}
