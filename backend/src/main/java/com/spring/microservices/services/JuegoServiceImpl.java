package com.spring.microservices.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.microservices.entity.Producto;
import com.spring.microservices.repository.JuegoRepository;

@Service
public class JuegoServiceImpl implements JuegoService {
	
	@Autowired
	JuegoRepository repository;

	@Transactional(readOnly = true)
	public List<Producto> allJuegos() {
		return repository.findAll();
	}
	
	@Transactional
	public Producto save(Producto producto) {
		return repository.save(producto);
	}
	
	@Transactional(readOnly = true)
	public Optional<Producto> findById(Long id) {
		return repository.findById(id);
	}
	
	
	
}
