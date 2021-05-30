package com.spring.microservices.entity.dto;

public class UsuarioDTO {
	private long id;
	private String email;
	private String password;
	private String nombre;
	private String apellidos;
	private String direccion;
	private boolean admin;

	public UsuarioDTO() {

	}

	public UsuarioDTO(long id, String email, String password, String nombre, String apellidos, String direccion,
			boolean admin) {

		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.direccion = direccion;
		this.admin = admin;
	}

	public long getId() {

		return id;
	}

	public void setId(long id) {

		this.id = id;
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
