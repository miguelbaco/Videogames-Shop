package com.spring.microservices.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Usuario {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_usuario")
	private long id;
	
	@Column(name="email", length = 50)
	private String email;
	
	@Column(name="password", length = 50)
	private String password;
	
	@Column(length = 25)
	private String nombre;
	
	@Column(length = 50)
	private String apellidos;
	
	@Column(length = 250)
	private String direccion;
	
	private boolean admin;
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Deseo> listadeseos = new ArrayList<>();

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
