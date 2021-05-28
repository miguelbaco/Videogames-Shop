import { Component, OnInit } from '@angular/core';
import { Usuario } from 'src/app/models/usuario';
import { DatosService } from 'src/app/services/datos.service';
import { UsuariosService } from 'src/app/services/usuarios.service';
import { environment } from 'src/environments/environment.prod';

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.css']
})
export class UsuarioComponent implements OnInit {

  usuariolog: Usuario;
  constructor(private datosService: DatosService, private usuarioService: UsuariosService) { }

  ngOnInit(): void {
    if(this.datosService.usuariologeado != null) {
      this.usuariolog = this.datosService.usuariologeado;
    }else {
      this.datosUsuario();
    }
  }

  datosUsuario() {
    if(sessionStorage.getItem("usuarioIDgamepoint") != null) {
      let idusuario = +sessionStorage.getItem("usuarioIDgamepoint");
      this.usuarioService.encontrarUsuario(idusuario).subscribe(
        (response) => {
          if(response.data != null) {
            this.datosService.usuariologeado = response.data;
            this.usuariolog = response.data;
          }
        }
      );
    } else {
      window.location.href= environment.url + "/notfound";
    }
  }

}
