package com.spring.microservices.utils;

import org.springframework.stereotype.Component;

import com.spring.microservices.entity.Categoria;
import com.spring.microservices.entity.Producto;
import com.spring.microservices.entity.dto.CategoriaDTO;
import com.spring.microservices.entity.dto.ProductoDTO;

@Component
public class BackendUtils {

	public Producto productoDTOtoProducto(ProductoDTO productoDTO) {

		Producto producto = new Producto();
		producto.setIdcategoria(Long.valueOf(productoDTO.getIdcategoria()));
		producto.setDescripcion(productoDTO.getDescripcion());
		producto.setImagen(productoDTO.getImagen());
		producto.setNombre(productoDTO.getNombre());
		producto.setPrecio(productoDTO.getPrecio());
		producto.setStock(productoDTO.getStock());
		return producto;
	}

	public Categoria CategoriaDTOtoCategoria(CategoriaDTO categoriaDTO) {

		Categoria categoria = new Categoria();
		categoria.setDescripcion(categoriaDTO.getDescripcion());
		categoria.setNombre(categoriaDTO.getNombre());
		return categoria;
	}

}
