import { Component, OnInit } from '@angular/core';
import { Error } from 'src/app/models/error';
import { Usuario } from 'src/app/models/usuario';
import { DatosService } from 'src/app/services/datos.service';
import { UsuariosService } from 'src/app/services/usuarios.service';
import * as $ from "jquery";
import { MenuComponent } from '../menu.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public usuario : Usuario;
  public notificarError: Error;
  public errorlogin : boolean;

  constructor(private usuarioService: UsuariosService, private datosService: DatosService) {
  }

  ngOnInit(): void {
    this.usuario = new Usuario;
    this.notificarError = new Error;
    this.errorlogin = false;
  }

  public loginUsuario(): void {

    this.usuarioService.loginUsuario(this.usuario).subscribe(
      (response) => {
        if(response.data != null) {
          this.usuario = response.data;
          sessionStorage.setItem('usuarioIDgamepoint', this.usuario.id.toString());
          this.datosService.usuariologeado = this.usuario;
          this.errorlogin = false;
          window.location.reload();
        }
      }, (error) => {
        this.notificarError = error.error.error[0];
        this.errorlogin = true;
      }, () => {
 
      }
    );
  }

}
