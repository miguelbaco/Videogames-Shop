package com.spring.microservices.entity.dto;

public class CategoriaDTO {

	private int id;
	private String nombre;
	private String descripcion;

	public CategoriaDTO(int id, String nombre, String descripcion) {

		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	public CategoriaDTO() {

		super();
	}

	public int getId() {

		return id;
	}

	public void setId(int id) {

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
