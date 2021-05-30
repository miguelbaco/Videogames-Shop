import { Pedido } from "./pedido";
import { Producto } from "./producto";

export class DetallePedido {
    pedido: Pedido;
    producto: Producto;
    cantidad: number;
    devuelto: boolean;
}
