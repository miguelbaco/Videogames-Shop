package com.spring.microservices.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@IdClass(DetallePedidoId.class)
@Table(name="detalle_pedido")
public class DetallePedido {
	
	@Id
    @ManyToOne
    @JoinColumn(
            name="id_pedido",
            insertable = false, updatable = false
    )
	private Pedido pedido;
	
	@Id
    @ManyToOne
    @JoinColumn(
            name="id_producto",
            insertable = false, updatable = false
    )
	private Producto producto;
	
	@Min(value = 1)
	@Max(value = 99)
	private int cantidad;
	
	private boolean devuelto;
	
	public Producto getProducto() {
	
		return producto;
	}

	
	public void setProducto(Producto producto) {
	
		this.producto = producto;
	}


	
	public Pedido getPedido() {
	
		return pedido;
	}


	
	public void setPedido(Pedido pedido) {
	
		this.pedido = pedido;
	}


	
	public int getCantidad() {
	
		return cantidad;
	}


	
	public void setCantidad(int cantidad) {
	
		this.cantidad = cantidad;
	}


	
	public boolean isDevuelto() {
	
		return devuelto;
	}


	
	public void setDevuleto(boolean devuelto) {
	
		this.devuelto = devuelto;
	}
}