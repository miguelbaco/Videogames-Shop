package com.spring.microservices.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@IdClass(DeseoId.class)
@Table(name="lista_deseos")
public class Deseo {
	
	@Id
    @ManyToOne
    @JoinColumn(
            name="id_usuario",
            insertable = false, updatable = false
    )
	private Usuario usuario;
	
	@Id
    @ManyToOne
    @JoinColumn(
            name="id_producto",
            insertable = false, updatable = false
    )
	private Producto producto;
	
	public Usuario getUsuario() {
	
		return usuario;
	}

	
	public void setUsuario(Usuario usuario) {
	
		this.usuario = usuario;
	}

	
	public Producto getProducto() {
	
		return producto;
	}

	
	public void setProducto(Producto producto) {
	
		this.producto = producto;
	}
}