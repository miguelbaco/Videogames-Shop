package com.spring.microservices.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.microservices.entity.Usuario;
import com.spring.microservices.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	UsuarioRepository repository;

	@Transactional(readOnly = true)
	public List<Usuario> allUsuarios() {
		return repository.findAll();
	}
	
	@Transactional
	public Usuario save(Usuario producto) {
		return repository.save(producto);
	}
	
	@Transactional(readOnly = true)
	public Optional<Usuario> findById(Long id) {
		return repository.findById(id);
	}
	
	public Optional<Usuario> findByEmail(String email) {
		return repository.findByEmail(email);
	}
}
