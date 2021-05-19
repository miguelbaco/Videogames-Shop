package com.spring.microservices.entity;

import java.io.Serializable;

public class ValoracionId implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long usuario;
    private Long producto;

    public ValoracionId() {

    }

	
	public Long getUsuario() {
	
		return usuario;
	}

	
	public void setUsuario(Long usuario) {
	
		this.usuario = usuario;
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
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		ValoracionId other = (ValoracionId) obj;
		if (producto == null) {
			if (other.producto != null)
				return false;
		} else if (!producto.equals(other.producto))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}
    
	
}