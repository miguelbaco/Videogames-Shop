package com.spring.microservices.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.microservices.entity.Categoria;
import com.spring.microservices.repository.CategoriaRepository;

@Service
public class CategoriaServiceImpl implements CategoriaService {
	
	@Autowired
	CategoriaRepository repository;

	@Transactional(readOnly = true)
	public List<Categoria> allCategorias() {
		return repository.findAll();
	}
	
	@Transactional
	public Categoria save(Categoria producto) {
		return repository.save(producto);
	}
	
	@Transactional(readOnly = true)
	public Optional<Categoria> findById(Long id) {
		return repository.findById(id);
	}
	
	
	
}
