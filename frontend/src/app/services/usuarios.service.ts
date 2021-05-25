import { Injectable } from '@angular/core';
import { Usuario } from '../models/usuario';
import { environment } from 'src/environments/environment';
import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { ResponseData } from '../models/responseData';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsuariosService {

  constructor(private http: HttpClient) { }

  public loginUsuario(usuario: Usuario): Observable<ResponseData> {
    let headers = new HttpHeaders();
    headers.set('Content-Type', 'application/json; charset=utf-8');
    headers.set("Access-Control-Allow-Methods","GET, POST, OPTIONS, PUT, DELETE");
    return this.http.post<ResponseData>(`${environment.apiUrl}/loginusuario`, usuario, { headers: headers });
  }

  public encontrarUsuario(idusuario: number): Observable<ResponseData> {
    let headers = new HttpHeaders();
    headers.set('Content-Type', 'application/json; charset=utf-8');
    headers.set("Access-Control-Allow-Methods","GET, POST, OPTIONS, PUT, DELETE");
    return this.http.get<ResponseData>(`${environment.apiUrl}/usuario/${idusuario}`, { headers: headers });
  }

  public registrarUsuario(usuario: Usuario): Observable<ResponseData> {
    let headers = new HttpHeaders();
    headers.set('Content-Type', 'application/json; charset=utf-8');
    headers.set("Access-Control-Allow-Methods","GET, POST, OPTIONS, PUT, DELETE");
    return this.http.post<ResponseData>(`${environment.apiUrl}/registrarusuario`, usuario, { headers: headers });
  }

  public allUsuarios(): Observable<ResponseData> {
    let headers = new HttpHeaders();
    headers.set('Content-Type', 'application/json; charset=utf-8');
    headers.set("Access-Control-Allow-Methods","GET, POST, OPTIONS, PUT, DELETE");
    return this.http.get<ResponseData>(`${environment.apiUrl}/usuarios`, { headers: headers });
  }
}


