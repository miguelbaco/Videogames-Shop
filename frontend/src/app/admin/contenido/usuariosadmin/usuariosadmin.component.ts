import { Component, OnInit } from '@angular/core';
import { Usuario } from 'src/app/models/usuario';
import { DatosService } from 'src/app/services/datos.service';
import { UsuariosService } from 'src/app/services/usuarios.service';

@Component({
  selector: 'app-usuariosadmin',
  templateUrl: './usuariosadmin.component.html',
  styleUrls: ['./usuariosadmin.component.css']
})
export class UsuariosadminComponent implements OnInit {

  listaUsuarios: Usuario[];

  constructor(private datosService: DatosService, private usuarioService: UsuariosService) { }

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

}
