package com.spring.microservices.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Categoria {
	
	@Id @GeneratedValue
	@Column(name="id_categoria")
	private long id;

	@Column(name="nombre_categoria", length = 20)
	private String nombre;
	
	@Column(name="descripcion_categoria", length = 250)
	private String descripcion;


	public long getId() {
	
		return id;
	}

	
	public void setId(long id) {
	
		this.id = id;
	}

	
	public String getNombre() {
	
		return nombre;
	}

	
	public void setNombre(String nombre) {
	
		this.nombre = nombre;
	}

	
	public String getDescripcion() {
	
		return descripcion;
	}

	
	public void setDescripcion(String descripcion) {
	
		this.descripcion = descripcion;
	}

}
