package com.spring.microservices.services;

import java.util.List;
import java.util.Optional;

import com.spring.microservices.entity.Pedido;

public interface PedidoService {

	public Optional<Pedido> findById(Long id);

	public Optional<Pedido> findByIdUsuarioAndComprado(Long idUsuario, boolean comprado);

	public Pedido save(Long idUsuario);

	public void realizarCompra(Pedido pedido);

	public List<Pedido> pedidosEnCamino(Long idUsuario);

	public List<Pedido> pedidosEnDevolucion(Long idUsuario);

	public List<Pedido> pedidosSinDevolucion(Long idUsuario);

}
