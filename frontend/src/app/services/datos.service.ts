import { Injectable } from '@angular/core';
import { Usuario } from '../models/usuario';

@Injectable({
  providedIn: 'root'
})
export class DatosService {

  usuariologeado: Usuario;
  constructor() { }
}
