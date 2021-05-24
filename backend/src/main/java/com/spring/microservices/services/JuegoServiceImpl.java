package com.spring.microservices.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.microservices.entity.Producto;
import com.spring.microservices.entity.Valoracion;
import com.spring.microservices.repository.JuegoRepository;
import com.spring.microservices.repository.ValoracionRepository;

@Service
public class JuegoServiceImpl implements JuegoService {
	
	@Autowired
	JuegoRepository repository;
	
	@Autowired
	ValoracionRepository valoracionRepository;

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

	public List<Producto> juegosByCategoria(int idcategoria) {
		return repository.findByIdCategoria(Long.valueOf(idcategoria));
	}
	
	public List<Valoracion> valoracionesJuego(Producto producto) {
		return valoracionRepository.findByProducto(producto);
	}
	
	
}
