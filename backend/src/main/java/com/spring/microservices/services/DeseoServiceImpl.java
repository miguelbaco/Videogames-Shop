package com.spring.microservices.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.microservices.entity.Deseo;
import com.spring.microservices.entity.Producto;
import com.spring.microservices.entity.Usuario;
import com.spring.microservices.repository.DeseoRepository;
import com.spring.microservices.repository.JuegoRepository;

@Service
public class DeseoServiceImpl implements DeseoService {
	
	@Autowired
	DeseoRepository repository;
	
	@Transactional(readOnly = true)
	public List<Deseo> findByIdUsuario(Long idusuario) {
		Deseo deseo = new Deseo();
		Usuario usuario = new Usuario();
		usuario.setId(idusuario);
		deseo.setUsuario(usuario);
		return repository.findByUsuario(usuario);
	}
	
	
	
}
