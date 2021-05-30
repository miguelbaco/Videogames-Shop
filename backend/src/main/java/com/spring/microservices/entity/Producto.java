package com.spring.microservices.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
public class Producto {

	@Id
	@GeneratedValue
	@Column(name = "id_producto")
	private long id;

	@Column(name = "nombre_producto", length = 25)
	@Size(max = 25)
	private String nombre;

	@Column(name = "descripcion_producto", length = 255)
	@Size(max = 255)
	private String descripcion;

	@DecimalMin(value = "0.01")
	@DecimalMin(value = "9999.99")
	private Double precio;

	@Min(value = 1)
	@Max(value = 999)
	private int stock;

	@Size(max = 100)
	private String imagen;

	@Column(name = "id_categoria")
	private Long idCategoria;

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

		return idCategoria;
	}

	public void setIdcategoria(Long idcategoria) {

		this.idCategoria = idcategoria;
	}

}
