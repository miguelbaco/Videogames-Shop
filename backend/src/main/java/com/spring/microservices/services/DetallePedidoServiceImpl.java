package com.spring.microservices.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.microservices.entity.DetallePedido;
import com.spring.microservices.entity.Pedido;
import com.spring.microservices.entity.Producto;
import com.spring.microservices.repository.DetallePedidoRepository;

@Service
public class DetallePedidoServiceImpl implements DetallePedidoService {
	
	@Autowired
	DetallePedidoRepository repository;
	
	public DetallePedido save(DetallePedido detallePedido) {
		return repository.save(detallePedido);
	}
	
	public List<DetallePedido> findByIdPedido(Long idPedido) {
		Pedido pedido = new Pedido();
		pedido.setId(idPedido);
		return repository.findByPedido(pedido);
	}
	
	public Optional<DetallePedido> findByPedidoAndProducto(Pedido pedido, Producto producto) {
		return repository.findByPedidoAndProducto(pedido, producto);
	}
	
	@Transactional
	public void delete(DetallePedido detallePedido) {
		repository.delete(detallePedido);
	}
}