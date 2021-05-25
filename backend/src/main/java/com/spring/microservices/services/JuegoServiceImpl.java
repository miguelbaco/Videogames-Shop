package com.spring.microservices.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.microservices.entity.Producto;
import com.spring.microservices.entity.Valoracion;
import com.spring.microservices.entity.dto.ProductoDTO;
import com.spring.microservices.repository.JuegoRepository;
import com.spring.microservices.repository.ValoracionRepository;

@Service
public class JuegoServiceImpl implements JuegoService {

	@Autowired
	JuegoRepository repository;

	@Autowired
	ValoracionRepository valoracionRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Producto> allJuegos() {

		return repository.findAll();
	}

	@Override
	@Transactional
	public Producto save(Producto producto) {

		return repository.save(producto);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Producto> findById(Long id) {

		return repository.findById(id);
	}

	@Override
	public List<Producto> juegosByCategoria(int idcategoria) {

		return repository.findByIdCategoria(Long.valueOf(idcategoria));
	}

	@Override
	public List<Valoracion> valoracionesJuego(Producto producto) {

		return valoracionRepository.findByProducto(producto);
	}

	@Override
	public void deleteJuego(Producto producto) {

		repository.delete(producto);
	}

	@Override
	public void updateJuego(ProductoDTO productoDTO) {

		Producto producto = repository.findById(Long.valueOf(productoDTO.getId()))
		        .orElseThrow(NoSuchElementException::new);
		producto.setDescripcion(productoDTO.getDescripcion());
		producto.setIdcategoria(Long.valueOf(productoDTO.getIdcategoria()));
		producto.setImagen(productoDTO.getImagen());
		producto.setNombre(productoDTO.getNombre());
		producto.setPrecio(productoDTO.getPrecio());
		producto.setStock(productoDTO.getStock());

		repository.save(producto);
	}

}
