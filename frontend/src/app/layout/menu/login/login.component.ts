import { Component, OnInit } from '@angular/core';
import { Error } from 'src/app/models/error';
import { Usuario } from 'src/app/models/usuario';
import { DatosService } from 'src/app/services/datos.service';
import { UsuariosService } from 'src/app/services/usuarios.service';
import { ActivatedRoute } from '@angular/router';
import { environment } from 'src/environments/environment.prod';
import * as $ from "jquery";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  /* Parametros de login */
  public usuario : Usuario;
  public notificarError: Error;
  public errorlogin : boolean;

  /* Parametros de registro */
  public usuarioregistrar : Usuario;
  public repitecontrasena: string;
  public registroexitoso: boolean;

  constructor(private usuarioService: UsuariosService, private datosService: DatosService, private ruta: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.usuario = new Usuario;
    this.notificarError = new Error;
    this.errorlogin = false;

    this.usuarioregistrar = new Usuario;
    this.registroexitoso = false;
  }

  public loginUsuario(): void {

    this.usuarioregistrar.id = null;

    this.usuarioService.loginUsuario(this.usuario).subscribe(
      (response) => {
        if(response.data != null) {
          this.usuario = response.data;
          sessionStorage.setItem('usuarioIDgamepoint', this.usuario.id.toString());
          this.datosService.usuariologeado = this.usuario;
          this.errorlogin = false;
          if(window.location.href.includes("/juego/") || window.location.href.includes("/categoria/") ) {
            window.location.href = environment.url;
          } else {
            window.location.reload();
          }
        }
      }, (error) => {
        this.notificarError = error.error.error[0];
        this.errorlogin = true;
      }
    );
  }

  public signinUsuario() : void {

    this.usuarioService.registrarUsuario(this.usuarioregistrar).subscribe(
      (response) => {
        if(response.data != null) {
          this.registroexitoso = true;
        }
      }, (error) => {
        this.notificarError = error.error.error[0];
        this.errorlogin = true;
      }
    );
  }

}
