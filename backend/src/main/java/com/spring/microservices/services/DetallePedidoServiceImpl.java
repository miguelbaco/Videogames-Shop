package com.spring.microservices.services;

import java.util.ArrayList;
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

	public List<Producto> recogerCarrito(Long idPedido) {

		List<DetallePedido> detalles = findByIdPedido(idPedido);

		/*Con este for compruebo el stock de los productos para bajar o eliminar los
		 * detalles según la cantidad de stock que haya conforme a la cantidad del detallePedido */
		for (DetallePedido detalle : detalles) {
			if (detalle.getProducto().getStock() < detalle.getCantidad()) {
				if (detalle.getProducto().getStock() == 0) {
					delete(detalle);
				} else {
					detalle.setCantidad(detalle.getProducto().getStock());
					save(detalle);
				}
			}
		}

		/* Ahora devuelvo los productos con las modificaciones hechas y lo devuelvo */
		List<DetallePedido> detallesdefinitivo = findByIdPedido(idPedido);
		List<Producto> productos = new ArrayList<Producto>();
		for (DetallePedido detalle : detallesdefinitivo) {
			for (int i = 0; i < detalle.getCantidad(); i++) {
				productos.add(detalle.getProducto());
			}
		}

		return productos;
	}

	public void devolverDetalleProducto(DetallePedido detallePedido) {
		detallePedido.setDevuelto(true); // Cambio a true el detallePedido.devuelto
		repository.save(detallePedido);
	}
}
