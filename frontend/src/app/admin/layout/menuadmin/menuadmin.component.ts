import { Component, OnInit } from '@angular/core';
import { DatosService } from 'src/app/services/datos.service';
import { UsuariosService } from 'src/app/services/usuarios.service';

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
            if(!this.datosService.usuariologeado.admin) {
              window.location.href="http://localhost:4200/";
            }
          }
        }
      );
    } else {
      window.location.href="http://localhost:4200/";
    }
  }

  cerrarsesion() {
    sessionStorage.removeItem("usuarioIDgamepoint");
    this.logeado = false;

    //TODO: Adaptar para despliegue y separar parte de admin para el componente de admin
    if(window.location.href == "http://localhost:4200/carrito" ||
     window.location.href == "http://localhost:4200/lista-de-deseos" || 
     window.location.href == "http://localhost:4200/usuario" ||
     window.location.href == "http://localhost:4200/compras" ||
     window.location.href == "http://localhost:4200/compras" ||
     window.location.href == "http://localhost:4200/adminjuegos" ||
     window.location.href == "http://localhost:4200/admincategorias" ||
     window.location.href == "http://localhost:4200/adminusuarios") {
      window.location.href="http://localhost:4200/";
    }
  }

}
