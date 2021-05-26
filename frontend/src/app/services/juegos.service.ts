import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
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

  public allJuegos(): Observable<ResponseData> {
    let headers = new HttpHeaders();
    headers.set('Content-Type', 'application/json; charset=utf-8');
    headers.set("Access-Control-Allow-Methods","GET, POST, OPTIONS, PUT, DELETE");
    return this.http.get<ResponseData>(`${environment.apiUrl}/juegos`, { headers: headers });
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
