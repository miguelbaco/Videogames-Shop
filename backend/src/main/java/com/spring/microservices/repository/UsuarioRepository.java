package com.spring.microservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.microservices.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
}