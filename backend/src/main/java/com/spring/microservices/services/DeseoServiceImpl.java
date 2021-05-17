package com.spring.microservices.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.microservices.entity.Deseo;
import com.spring.microservices.entity.Usuario;
import com.spring.microservices.repository.DeseoRepository;

@Service
public class DeseoServiceImpl implements DeseoService {
	
	@Autowired
	DeseoRepository repository;
	
	@Transactional(readOnly = true)
	public List<Deseo> findByIdUsuario(Long idusuario) {
		Usuario usuario = new Usuario();
		usuario.setId(idusuario);
		return repository.findByUsuario(usuario);
	}
	
	@Transactional
	public Deseo save(Deseo deseo) {
		return repository.save(deseo);
	}
	
}
