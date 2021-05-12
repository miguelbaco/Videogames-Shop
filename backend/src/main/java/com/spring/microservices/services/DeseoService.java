package com.spring.microservices.services;

import java.util.List;
import java.util.Optional;

import com.spring.microservices.entity.Deseo;
import com.spring.microservices.entity.Producto;

public interface DeseoService {

	public List<Deseo> findByIdUsuario(Long idusuario);
}
