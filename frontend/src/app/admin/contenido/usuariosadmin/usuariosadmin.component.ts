import { Component, OnInit } from '@angular/core';
import { Usuario } from 'src/app/models/usuario';
import { AdminService } from 'src/app/services/admin.service';
import { UsuariosService } from 'src/app/services/usuarios.service';

@Component({
  selector: 'app-usuariosadmin',
  templateUrl: './usuariosadmin.component.html',
  styleUrls: ['./usuariosadmin.component.css']
})
export class UsuariosadminComponent implements OnInit {

  listaUsuarios: Usuario[];

  usuarioregistrar: Usuario = new Usuario();
  adminregistrar: boolean = false;
  public repitecontrasena: string;

  usuariomodificar: Usuario = new Usuario();
  
  registroexitoso: Boolean = false;
  notificarError = new Error;
  errorlogin = false;

  constructor(private adminService: AdminService, private usuarioService: UsuariosService) { }

  ngOnInit(): void {
    this.allUsuarios();
  }

  allUsuarios() {
      this.usuarioService.allUsuarios().subscribe(
        (response) => {
          this.listaUsuarios = response.data;
        }
      ); 
  }

  public signinusuario() : void {

    this.usuarioregistrar.admin = this.adminregistrar;
    this.usuarioService.registrarUsuario(this.usuarioregistrar).subscribe(
      (response) => {
        if(response.data != null) {
          this.registroexitoso = true;
          // Para no tener que volver a recogerlo de backend se añade a mano
          this.listaUsuarios.push(this.usuarioregistrar);
          this.repitecontrasena = "";
          this.usuarioregistrar = new Usuario(); // A null de nuevo para poder añadir nuevos
        }
      }, (error) => {
        this.notificarError = error.error.error[0];
        this.errorlogin = true;
      }
    );
  }

  cambiaradmin() { // Evento de click para despues al hacer siginusuario meterle el booleano
    if(this.adminregistrar == false) {
      this.adminregistrar = true;
    } else {
      this.adminregistrar = true;
    }
  }

  usuarioamodificar(usuario: Usuario) { // Evento de click de modificar para mostrar datos del usuario
    this.usuariomodificar = usuario;
  }

  modificarusuario() { //Modifica el usuario en BD
    if(sessionStorage.getItem("usuarioIDgamepoint") != null) {
          let idusuario = +sessionStorage.getItem("usuarioIDgamepoint");
          this.adminService.modificarUsuario(this.usuariomodificar, idusuario).subscribe();
    }
  }
  
}
