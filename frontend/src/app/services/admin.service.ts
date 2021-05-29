import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.prod';
import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { ResponseData } from '../models/responseData';
import { Observable } from 'rxjs';
import { Producto } from '../models/producto';
import { Categoria } from '../models/categoria';
import { Usuario } from '../models/usuario';


@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) { }

  public allJuegos(): Observable<ResponseData> {
    let headers = new HttpHeaders();
    headers.set('Content-Type', 'application/json; charset=utf-8');
    headers.set("Access-Control-Allow-Methods","GET, POST, OPTIONS, PUT, DELETE");
    return this.http.get<ResponseData>(`${environment.apiUrl}/adminjuegos`, { headers: headers });
  }

  public eliminarJuego(juego: Producto, idusuario: number): Observable<ResponseData> {
    let headers = new HttpHeaders();
    headers.set('Content-Type', 'application/json; charset=utf-8');
    headers.set("Access-Control-Allow-Methods","GET, POST, OPTIONS, PUT, DELETE");
    return this.http.post<ResponseData>(`${environment.apiUrl}/admineliminarjuego/${idusuario}`, juego, { headers: headers });
  }

  public anadirJuego(juego: Producto, idusuario: number): Observable<ResponseData> {
    let headers = new HttpHeaders();
    headers.set('Content-Type', 'application/json; charset=utf-8');
    headers.set("Access-Control-Allow-Methods","GET, POST, OPTIONS, PUT, DELETE");
    return this.http.post<ResponseData>(`${environment.apiUrl}/adminanadirjuego/${idusuario}`, juego, { headers: headers });
  }

  public subirImagen(file: File): Observable<ResponseData> {
    let headers = new HttpHeaders();
    headers.set('Content-Type', 'application/json; charset=utf-8');
    headers.set("Access-Control-Allow-Methods","GET, POST, OPTIONS, PUT, DELETE");
    let formData: FormData = new FormData();
    formData.append('file', file);
    return this.http.post<ResponseData>(`${environment.apiUrl}/subirimagen`, formData, { headers: headers });
  }

  public modificarJuego(juego: Producto, idusuario: number): Observable<ResponseData> {
    let headers = new HttpHeaders();
    headers.set('Content-Type', 'application/json; charset=utf-8');
    headers.set("Access-Control-Allow-Methods","GET, POST, OPTIONS, PUT, DELETE");
    return this.http.post<ResponseData>(`${environment.apiUrl}/admineditarjuego/${idusuario}`, juego, { headers: headers });
  }

  public anadirCategoria(categoria: Categoria, idusuario: number): Observable<ResponseData> {
    let headers = new HttpHeaders();
    headers.set('Content-Type', 'application/json; charset=utf-8');
    headers.set("Access-Control-Allow-Methods","GET, POST, OPTIONS, PUT, DELETE");
    return this.http.post<ResponseData>(`${environment.apiUrl}/adminanadircategoria/${idusuario}`, categoria, { headers: headers });
  }

  public modificarCategoria(categoria: Categoria, idusuario: number): Observable<ResponseData> {
    let headers = new HttpHeaders();
    headers.set('Content-Type', 'application/json; charset=utf-8');
    headers.set("Access-Control-Allow-Methods","GET, POST, OPTIONS, PUT, DELETE");
    return this.http.post<ResponseData>(`${environment.apiUrl}/admineditarcategoria/${idusuario}`, categoria, { headers: headers });
  }

  public modificarUsuario(usuario: Usuario, idusuario: number): Observable<ResponseData> {
    let headers = new HttpHeaders();
    headers.set('Content-Type', 'application/json; charset=utf-8');
    headers.set("Access-Control-Allow-Methods","GET, POST, OPTIONS, PUT, DELETE");
    return this.http.post<ResponseData>(`${environment.apiUrl}/admineditarusuario/${idusuario}`, usuario, { headers: headers });
  }

}