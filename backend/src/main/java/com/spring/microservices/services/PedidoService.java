package com.spring.microservices.services;

import java.util.Optional;

import com.spring.microservices.entity.Pedido;

public interface PedidoService {
	
	public Optional<Pedido> findByIdUsuarioAndComprado(Long idUsuario, boolean comprado);
	
	public Pedido save(Long idUsuario);
}
