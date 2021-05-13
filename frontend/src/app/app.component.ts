import {Component, ViewEncapsulation} from '@angular/core';
import { Router } from '@angular/router';
import { DatosService } from './services/datos.service';
import { UsuariosService } from './services/usuarios.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class AppComponent {
  title = 'frontent';

  constructor(public router: Router) {

  }

}