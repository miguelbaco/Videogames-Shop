import { Component, OnInit } from '@angular/core';
import { DatosService } from 'src/app/services/datos.service';
import { UsuariosService } from 'src/app/services/usuarios.service';
import { environment } from 'src/environments/environment.prod';

@Component({
  selector: 'app-menuadmin',
  templateUrl: './menuadmin.component.html',
  styleUrls: ['./menuadmin.component.css']
})
export class MenuadminComponent implements OnInit {
  logeado: boolean;
  nombreusuario: string;

  constructor(private datosService: DatosService, private usuarioService: UsuariosService) { }

  ngOnInit(): void {
    this.nombreusuario = "";
    if(sessionStorage.getItem("usuarioIDgamepoint") != null) {
      let idusuario = +sessionStorage.getItem("usuarioIDgamepoint");
      this.usuarioService.encontrarUsuario(idusuario).subscribe(
        (response) => {
          if(response.data != null) {
            this.datosService.usuariologeado = response.data;
            this.nombreusuario = this.datosService.usuariologeado.nombre;
            this.logeado = true;
            if(!this.datosService.usuariologeado.admin) { // Si no es admin lo lleva a index
              window.location.href= environment.url;
            }
          }
        }
      );
    } else {// Si no esta logeado lo lleva a index
      window.location.href= environment.url;
    }
  }

  cerrarsesion() {
    sessionStorage.removeItem("usuarioIDgamepoint");
    this.logeado = false;

    // Si cierra sesión y se encuentra en estos sitios vuelve a index
    if(window.location.href == environment.url + "/carrito" ||
     window.location.href == environment.url + "/lista-de-deseos" || 
     window.location.href == environment.url + "/usuario" ||
     window.location.href == environment.url + "/compras" ||
     window.location.href == environment.url + "/compras" ||
     window.location.href == environment.url + "/adminjuegos" ||
     window.location.href == environment.url + "/admincategorias" ||
     window.location.href == environment.url + "/adminusuarios") {
      window.location.href= environment.url;
    }
  }

}
