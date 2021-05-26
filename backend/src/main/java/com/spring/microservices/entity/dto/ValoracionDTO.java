package com.spring.microservices.entity.dto;

public class ValoracionDTO {
	private UsuarioDTO usuario;
	private ProductoDTO producto;
	private String comentario;
	private int puntuacion;
	
	public ValoracionDTO() {
		
	}

	public ValoracionDTO(UsuarioDTO usuario, ProductoDTO producto, String comentario, int puntuacion) {
		super();
		this.usuario = usuario;
		this.producto = producto;
		this.comentario = comentario;
		this.puntuacion = puntuacion;
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	public ProductoDTO getProducto() {
		return producto;
	}

	public void setProducto(ProductoDTO producto) {
		this.producto = producto;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public int getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}
	
	
	
}
