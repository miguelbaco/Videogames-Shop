package com.spring.microservices.services;

import java.util.List;
import java.util.Optional;

import com.spring.microservices.entity.Categoria;

public interface CategoriaService {

	public List<Categoria> allCategorias();

	public Categoria save(Categoria categoria);

	public Optional<Categoria> findById(Long id);
}
