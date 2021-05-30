package com.spring.microservices.entity.dto;

public class ProductoDTO {

	int id;
	String nombre;
	String descripcion;
	double precio;
	int stock;
	String imagen;
	int idcategoria;
	String nombrecategoria;
	int cantidad;

	public ProductoDTO() {

		super();
	}

	public ProductoDTO(int id, String nombre, String descripcion, double precio, int stock, String imagen,
			int idcategoria, String nombrecategoria, int cantidad) {

		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.stock = stock;
		this.imagen = imagen;
		this.idcategoria = idcategoria;
		this.nombrecategoria = nombrecategoria;
		this.cantidad = cantidad;
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

	public double getPrecio() {

		return precio;
	}

	public void setPrecio(double precio) {

		this.precio = precio;
	}

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

	public int getIdcategoria() {

		return idcategoria;
	}

	public void setIdcategoria(int idcategoria) {

		this.idcategoria = idcategoria;
	}

	public String getNombrecategoria() {

		return nombrecategoria;
	}

	public void setNombrecategoria(String nombrecategoria) {

		this.nombrecategoria = nombrecategoria;
	}

	public int getCantidad() {

		return cantidad;
	}

	public void setCantidad(int cantidad) {

		this.cantidad = cantidad;
	}

}
