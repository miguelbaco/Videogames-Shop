package com.spring.microservices.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Producto {
	
	@Id @GeneratedValue
	@Column(name="id_producto")
	private long id;
	
	@Column(name="nombre_producto")
	private String nombre;
	
	private String descripcion;
	
	private Double precio;
	
	private Double stock;
	
	private String imagen;
	
	@Column(name="id_categoria")
	private Long idcategoria;

	
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

	
	public Double getPrecio() {
	
		return precio;
	}

	
	public void setPrecio(Double precio) {
	
		this.precio = precio;
	}

}
