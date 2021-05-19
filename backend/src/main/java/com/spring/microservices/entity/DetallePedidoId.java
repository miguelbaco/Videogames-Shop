package com.spring.microservices.entity;

import java.io.Serializable;

public class DetallePedidoId implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long pedido;
    private Long producto;

    public DetallePedidoId() {

    }

	
	public Long getPedido() {
	
		return pedido;
	}

	
	public void setPedido(Long pedido) {
	
		this.pedido = pedido;
	}

	
	public Long getProducto() {
	
		return producto;
	}

	
	public void setProducto(Long producto) {
	
		this.producto = producto;
	}


	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((producto == null) ? 0 : producto.hashCode());
		result = prime * result + ((pedido == null) ? 0 : pedido.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DetallePedidoId other = (DetallePedidoId) obj;
		if (producto == null) {
			if (other.producto != null)
				return false;
		} else if (!producto.equals(other.producto))
			return false;
		if (pedido == null) {
			if (other.pedido != null)
				return false;
		} else if (!pedido.equals(other.pedido))
			return false;
		return true;
	}
    
	
}