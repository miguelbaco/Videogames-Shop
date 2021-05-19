package com.spring.microservices.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;


@Entity
@IdClass(ValoracionId.class)
@Table(name="valoracion")
public class Valoracion {
	
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
	
	@Min(value = 0)
	@Max(value = 5)
	private int puntuacion;
	
	@Size(max = 300)
	private boolean comentario;
	
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


	
	public int getPuntuacion() {
	
		return puntuacion;
	}


	
	public void setPuntuacion(int puntuacion) {
	
		this.puntuacion = puntuacion;
	}


	
	public boolean isComentario() {
	
		return comentario;
	}


	
	public void setComentario(boolean comentario) {
	
		this.comentario = comentario;
	}
}