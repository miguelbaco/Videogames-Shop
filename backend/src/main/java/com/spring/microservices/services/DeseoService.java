package com.spring.microservices.services;

import java.util.List;
import java.util.Optional;

import com.spring.microservices.entity.Deseo;
import com.spring.microservices.entity.Producto;
import com.spring.microservices.entity.Usuario;

public interface DeseoService {

	public List<Deseo> findByIdUsuario(Long idusuario);

	public Deseo save(Deseo deseo);

	public Optional<Deseo> findByUsuarioAndProducto(Usuario usuario, Producto Producto);

	public void delete(Deseo deseo);
}
