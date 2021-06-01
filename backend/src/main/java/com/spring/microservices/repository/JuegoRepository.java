package com.spring.microservices.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.microservices.entity.Producto;

public interface JuegoRepository extends JpaRepository<Producto, Long> {

	List<Producto> findByIdCategoria(Long idCategoria);
	List<Producto> findByNombreContaining(String name);
}