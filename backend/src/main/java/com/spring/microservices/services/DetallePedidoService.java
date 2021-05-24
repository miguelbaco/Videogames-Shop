package com.spring.microservices.services;

import java.util.List;
import java.util.Optional;

import com.spring.microservices.entity.DetallePedido;
import com.spring.microservices.entity.Pedido;
import com.spring.microservices.entity.Producto;

public interface DetallePedidoService {
	
	public DetallePedido save(DetallePedido detallePedido);
	
	public List<DetallePedido> findByIdPedido(Long idPedido);
	
	public Optional<DetallePedido> findByPedidoAndProducto(Pedido pedido, Producto producto);
	
	public void delete(DetallePedido detallePedido);
	
	public List<Producto> productosComprados(List<Pedido> pedidos);
}
