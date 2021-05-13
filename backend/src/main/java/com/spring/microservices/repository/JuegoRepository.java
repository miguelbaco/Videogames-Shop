package com.spring.microservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.microservices.entity.Producto;

public interface JuegoRepository extends JpaRepository<Producto, Long>{

	//List<Producto> findByNameContaining(String name);
}