package com.spring.microservices.services;

import java.util.List;
import java.util.Optional;

import com.spring.microservices.entity.Producto;
import com.spring.microservices.entity.Valoracion;
import com.spring.microservices.entity.dto.ProductoDTO;

public interface JuegoService {

	public List<Producto> allJuegos();

	public Producto save(Producto producto);

	public Optional<Producto> findById(Long id);

	public List<Producto> juegosByCategoria(int idcategoria);

	public List<Valoracion> valoracionesJuego(Producto producto);

	public void deleteJuego(Producto producto);

	public void updateJuego(ProductoDTO productoDTO);
}
