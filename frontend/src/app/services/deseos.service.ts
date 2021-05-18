import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { ResponseData } from '../models/responseData';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DeseosService {

  constructor(private http: HttpClient) { }

  public DeseosUsuario(idusuario: number): Observable<ResponseData> {
    let headers = new HttpHeaders();
    headers.set('Content-Type', 'application/json; charset=utf-8');
    headers.set("Access-Control-Allow-Methods","GET, POST, OPTIONS, PUT, DELETE");
    return this.http.get<ResponseData>(`${environment.apiUrl}/deseos/${idusuario}`, { headers: headers });
  }

  public anadirDeseo(idusuario: number, idjuego: number): Observable<ResponseData> {
    let headers = new HttpHeaders();
    headers.set('Content-Type', 'application/json; charset=utf-8');
    headers.set("Access-Control-Allow-Methods","GET, POST, OPTIONS, PUT, DELETE");
    return this.http.get<ResponseData>(`${environment.apiUrl}/anadirdeseo/${idusuario}/${idjuego}`, { headers: headers });
  }

  public eliminarDeseo(idusuario: number, idjuego: number): Observable<ResponseData> {
    let headers = new HttpHeaders();
    headers.set('Content-Type', 'application/json; charset=utf-8');
    headers.set("Access-Control-Allow-Methods","GET, POST, OPTIONS, PUT, DELETE");
    return this.http.get<ResponseData>(`${environment.apiUrl}/eliminardeseo/${idusuario}/${idjuego}`, { headers: headers });
  }
}
