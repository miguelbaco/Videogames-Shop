package com.spring.microservices.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.microservices.entity.Categoria;
import com.spring.microservices.entity.dto.CategoriaDTO;
import com.spring.microservices.repository.CategoriaRepository;

@Service
public class CategoriaServiceImpl implements CategoriaService {

	@Autowired
	CategoriaRepository repository;

	@Override
	@Transactional(readOnly = true)
	public List<Categoria> allCategorias() {

		return repository.findAll();
	}

	@Override
	@Transactional
	public Categoria save(Categoria categoria) {

		return repository.save(categoria);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Categoria> findById(Long id) {

		return repository.findById(id);
	}

	@Override
	public void updateCategoria(CategoriaDTO categoriaDTO) {

		Categoria categoria = repository.findById(Long.valueOf(categoriaDTO.getId()))
				.orElseThrow(NoSuchElementException::new);
		categoria.setDescripcion(categoriaDTO.getDescripcion());
		categoria.setNombre(categoriaDTO.getNombre());

		repository.save(categoria);
	}

}
