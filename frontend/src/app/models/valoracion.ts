import { Producto } from "./producto";
import { Usuario } from "./usuario";

export class Valoracion {
    usuario: Usuario;
    producto: Producto;
    puntuacion: number;
    comentario: string;
}
