package com.spring.microservices.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.microservices.entity.Deseo;
import com.spring.microservices.entity.Producto;
import com.spring.microservices.entity.Usuario;

public interface DeseoRepository extends JpaRepository<Deseo, Long>{
		
	public List<Deseo> findByUsuario(Usuario usuario);
	
	public Optional<Deseo> findByUsuarioAndProducto(Usuario usuario, Producto producto);
}