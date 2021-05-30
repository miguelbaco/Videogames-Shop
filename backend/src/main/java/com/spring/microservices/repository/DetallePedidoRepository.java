package com.spring.microservices.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.microservices.entity.DetallePedido;
import com.spring.microservices.entity.Pedido;
import com.spring.microservices.entity.Producto;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {

	public List<DetallePedido> findByPedido(Pedido pedido);

	public Optional<DetallePedido> findByPedidoAndProducto(Pedido pedido, Producto Producto);
}