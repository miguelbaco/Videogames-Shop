import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.prod';
import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { ResponseData } from '../models/responseData';
import { Observable } from 'rxjs';
import { Valoracion } from '../models/valoracion';

@Injectable({
  providedIn: 'root'
})
export class JuegosService {

  constructor(private http: HttpClient) {
  }

  public allJuegos(aleatory?: boolean): Observable<ResponseData> {
    let headers = new HttpHeaders();
    headers.set('Content-Type', 'application/json; charset=utf-8');
    headers.set("Access-Control-Allow-Methods","GET, POST, OPTIONS, PUT, DELETE");
    let novedades: boolean = false;
    if(aleatory == true) {
      novedades = aleatory;
    }
    return this.http.get<ResponseData>(`${environment.apiUrl}/juegos/${novedades}`, { headers: headers });
  }

  public juego(idjuego: number): Observable<ResponseData> {
    let headers = new HttpHeaders();
    headers.set('Content-Type', 'application/json; charset=utf-8');
    headers.set("Access-Control-Allow-Methods","GET, POST, OPTIONS, PUT, DELETE");
    return this.http.get<ResponseData>(`${environment.apiUrl}/juego/` + idjuego, { headers: headers });
  }

  public juegosCategoria(idcategoria: number): Observable<ResponseData> {
    let headers = new HttpHeaders();
    headers.set('Content-Type', 'application/json; charset=utf-8');
    headers.set("Access-Control-Allow-Methods","GET, POST, OPTIONS, PUT, DELETE");
    return this.http.get<ResponseData>(`${environment.apiUrl}/juegosporcategoria/` + idcategoria, { headers: headers });
  }

  public valoracionesJuego(idjuego: number): Observable<ResponseData> {
    let headers = new HttpHeaders();
    headers.set('Content-Type', 'application/json; charset=utf-8');
    headers.set("Access-Control-Allow-Methods","GET, POST, OPTIONS, PUT, DELETE");
    return this.http.get<ResponseData>(`${environment.apiUrl}/valoraciones/` + idjuego, { headers: headers });
  }

  public anadirValoracion(valoracion: Valoracion): Observable<ResponseData> {
    let headers = new HttpHeaders();
    headers.set('Content-Type', 'application/json; charset=utf-8');
    headers.set("Access-Control-Allow-Methods","GET, POST, OPTIONS, PUT, DELETE");
    return this.http.post<ResponseData>(`${environment.apiUrl}/anadirvaloracion/`, valoracion, { headers: headers });
  }
}
