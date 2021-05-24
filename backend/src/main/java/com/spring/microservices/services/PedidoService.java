package com.spring.microservices.services;

import java.util.List;
import java.util.Optional;

import com.spring.microservices.entity.Pedido;

public interface PedidoService {
	
	public Optional<Pedido> findByIdUsuarioAndComprado(Long idUsuario, boolean comprado);
	
	public List<Pedido> pedidosRealizados(Long idUsuario);
	
	public Pedido save(Long idUsuario);
	
	public void realizarCompra(Pedido pedido);
}
