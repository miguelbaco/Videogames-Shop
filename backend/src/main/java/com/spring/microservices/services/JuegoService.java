package com.spring.microservices.services;

import java.util.List;
import java.util.Optional;

import com.spring.microservices.entity.Producto;

public interface JuegoService {
	
	public List<Producto> allJuegos();
	
	public Producto save(Producto producto);

	public Optional<Producto> findById(Long id);
}
