package com.spring.microservices.entity.dto;

public class UsuarioDTO {
	private long id_usuario;
	private String email;
	private String password;
	private String nombre;
	private String apellidos;
	private String direccion;
	private boolean admin;
	
	public UsuarioDTO() {
		
	}
	
	public UsuarioDTO(long id_usuario,
	                  String email,
	                  String password,
	                  String nombre,
	                  String apellidos,
	                  String direccion,
	                  boolean admin) {

		super();
		this.id_usuario = id_usuario;
		this.email = email;
		this.password = password;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.direccion = direccion;
		this.admin = admin;
	}
	
	public long getId_usuario() {
	
		return id_usuario;
	}

	
	public void setId_usuario(long id_usuario) {
	
		this.id_usuario = id_usuario;
	}

	public String getEmail() {
	
		return email;
	}

	
	public void setEmail(String email) {
	
		this.email = email;
	}

	
	public String getPassword() {
	
		return password;
	}

	
	public void setPassword(String password) {
	
		this.password = password;
	}

	
	public String getNombre() {
	
		return nombre;
	}

	
	public void setNombre(String nombre) {
	
		this.nombre = nombre;
	}

	
	public String getApellidos() {
	
		return apellidos;
	}

	
	public void setApellidos(String apellidos) {
	
		this.apellidos = apellidos;
	}

	
	public String getDireccion() {
	
		return direccion;
	}

	
	public void setDireccion(String direccion) {
	
		this.direccion = direccion;
	}

	
	public boolean isAdmin() {
	
		return admin;
	}

	
	public void setAdmin(boolean admin) {
	
		this.admin = admin;
	}
	
	
	
}
