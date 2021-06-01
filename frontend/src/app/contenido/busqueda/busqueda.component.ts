import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DatosService } from 'src/app/services/datos.service';

@Component({
  selector: 'app-busqueda',
  templateUrl: './busqueda.component.html',
  styleUrls: ['./busqueda.component.css']
})
export class BusquedaComponent implements OnInit {

  public busqueda: string;

  constructor(private datosService: DatosService) { }

  ngOnInit(): void {
    this.busqueda = this.datosService.busqueda;
  }

}
