package com.spring.microservices.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
public class Pedido {
	
	@Id @GeneratedValue
	@Column(name="id_pedido")
	private long id;
	
	@Column(name="comprado", columnDefinition = "boolean default false")
	private boolean comprado;
	
	@Column(name="fecha")
	@Size(max = 10)
	private String fecha;
	
	@Column(name="id_usuario")
	private Long idUsuario;

	public Pedido(long id, boolean comprado, @Size(max = 10) String fecha, Long idUsuario) {

		super();
		this.id = id;
		this.comprado = comprado;
		this.fecha = fecha;
		this.idUsuario = idUsuario;
	}


	public Pedido() {

		super();
	}


	public long getId() {
	
		return id;
	}

	
	public void setId(long id) {
	
		this.id = id;
	}

	
	public boolean isComprado() {
	
		return comprado;
	}

	
	public void setComprado(boolean comprado) {
	
		this.comprado = comprado;
	}

	
	public String getFecha() {
	
		return fecha;
	}

	
	public void setFecha(String fecha) {
	
		this.fecha = fecha;
	}

	
	public Long getIdUsuario() {
	
		return idUsuario;
	}

	
	public void setIdUsuario(Long idUsuario) {
	
		this.idUsuario = idUsuario;
	}

}
