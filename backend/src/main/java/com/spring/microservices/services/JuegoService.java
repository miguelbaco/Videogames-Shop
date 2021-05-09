package com.spring.microservices.services;

import java.util.List;

import com.spring.microservices.entity.Producto;

public interface JuegoService {
	
	List<Producto> allJuegos();
	
}
