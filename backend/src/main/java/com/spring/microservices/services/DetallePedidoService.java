package com.spring.microservices.services;

import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import com.spring.microservices.entity.DetallePedido;
import com.spring.microservices.entity.Pedido;
import com.spring.microservices.entity.Producto;
import com.spring.microservices.entity.Usuario;

public interface DetallePedidoService {

	public DetallePedido save(DetallePedido detallePedido);

	public List<DetallePedido> findByIdPedido(Long idPedido);

	public Optional<DetallePedido> findByPedidoAndProducto(Pedido pedido, Producto producto);

	public void delete(DetallePedido detallePedido);

	public List<Producto> recogerCarrito(Long idPedido);

	public void devolverDetalleProducto(DetallePedido detallePedido);
	
	public void sendEmail(Usuario usuario, Pedido pedido) throws MessagingException;
}
