package com.spring.microservices.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;

@Entity
public class Producto {
	
	@Id @GeneratedValue
	@Column(name="id_producto")
	private long id;
	
	@Column(name="nombre_producto", length = 25)
	private String nombre;
	
	@Column(name="descripcion_producto", length = 250)
	private String descripcion;
	
	private Double precio;
	
	private int stock;
	
	@Column(length = 25)
	private String imagen;
	
	@Column(name="id_categoria")
	private Long idcategoria;
	
	@OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Deseo> listadeseos = new ArrayList<>();
	
	public int getStock() {
	
		return stock;
	}


	
	public void setStock(int stock) {
	
		this.stock = stock;
	}


	
	public String getImagen() {
	
		return imagen;
	}


	
	public void setImagen(String imagen) {
	
		this.imagen = imagen;
	}


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



	
	public Long getIdcategoria() {
	
		return idcategoria;
	}



	
	public void setIdcategoria(Long idcategoria) {
	
		this.idcategoria = idcategoria;
	}

}
