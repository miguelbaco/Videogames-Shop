package com.spring.microservices.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.microservices.entity.Producto;
import com.spring.microservices.entity.Valoracion;

public interface ValoracionRepository extends JpaRepository<Valoracion, Long> {

	public List<Valoracion> findByProducto(Producto producto);
}