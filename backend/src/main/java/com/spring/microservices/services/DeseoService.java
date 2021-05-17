package com.spring.microservices.services;

import java.util.List;

import com.spring.microservices.entity.Deseo;

public interface DeseoService {

	public List<Deseo> findByIdUsuario(Long idusuario);
	
	public Deseo save(Deseo deseo);
}
