package com.spring.microservices.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.spring.microservices.entity.DetallePedido;
import com.spring.microservices.entity.Pedido;
import com.spring.microservices.entity.Producto;
import com.spring.microservices.entity.Usuario;
import com.spring.microservices.entity.dto.ErrorDTO;
import com.spring.microservices.entity.dto.ResponseDTO;
import com.spring.microservices.services.DetallePedidoService;
import com.spring.microservices.services.JuegoService;
import com.spring.microservices.services.PedidoService;
import com.spring.microservices.services.UsuarioService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class DetallePedidoController {

	private static final Logger log = LoggerFactory.getLogger(DetallePedidoController.class);

	@Autowired
	PedidoService pedidoService;

	@Autowired
	JuegoService juegoService;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	DetallePedidoService detallePedidoService;

	@GetMapping("carritoactual/{idusuario}")
	public ResponseEntity<ResponseDTO> carritoActual(@PathVariable int idusuario) {

		ResponseDTO responseDTO = new ResponseDTO();

		Optional<Usuario> usuario = usuarioService.findById(Long.valueOf(idusuario));

		if (!usuario.isPresent()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "No existe el usuario que estas buscando", ErrorDTO.CODE_ERROR_JUEGO,
					log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
		}

		Optional<Pedido> pedido = pedidoService.findByIdUsuarioAndComprado(usuario.get().getId(), false);

		if (!pedido.isPresent()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "No hay nada en tu carrito todavía", ErrorDTO.CODE_ERROR_JUEGO, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
		}

		/* Con esta función recojo todos los productos ligados a un idPedido */
		List<Producto> productos = detallePedidoService.recogerCarrito(pedido.get().getId());

		responseDTO.setData(productos);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("realizarcompra/{idusuario}")
	public ResponseEntity<ResponseDTO> comprarCarrito(@PathVariable int idusuario) {

		ResponseDTO responseDTO = new ResponseDTO();

		Optional<Usuario> usuario = usuarioService.findById(Long.valueOf(idusuario));

		if (!usuario.isPresent()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "No existe el usuario que estas buscando", ErrorDTO.CODE_ERROR_JUEGO,
					log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
		}

		Optional<Pedido> pedido = pedidoService.findByIdUsuarioAndComprado(usuario.get().getId(), false);

		if (!pedido.isPresent()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "No hay nada en tu carrito todavía", ErrorDTO.CODE_ERROR_JUEGO, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
		}

		List<DetallePedido> detalles = detallePedidoService.findByIdPedido(pedido.get().getId());

		if (detalles.isEmpty()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "No hay nada para comprar", ErrorDTO.CODE_ERROR_JUEGO, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
		}

		for (DetallePedido detalle : detalles) {
			Producto producto = detalle.getProducto();
			// Aqui resto el stock de cada producto por la cantidad comprada
			producto.setStock(producto.getStock() - detalle.getCantidad());
			juegoService.save(producto);
		}

		pedidoService.realizarCompra(pedido.get()); // Aqui marco nueva fecha (actual) y true como comprado

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("anadircarrito/{idusuario}/{idjuego}")
	public ResponseEntity<ResponseDTO> anadirCarrito(@PathVariable int idusuario, @PathVariable int idjuego) {

		ResponseDTO responseDTO = new ResponseDTO();

		Optional<Usuario> usuario = usuarioService.findById(Long.valueOf(idusuario));
		Optional<Producto> juego = juegoService.findById(Long.valueOf(idjuego));

		if (!usuario.isPresent()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "No existe el usuario que estas buscando", ErrorDTO.CODE_ERROR_JUEGO,
					log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
		} else if (!juego.isPresent()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "No existe el juego que estas buscando", ErrorDTO.CODE_ERROR_JUEGO, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
		}

		Optional<Pedido> pedido = pedidoService.findByIdUsuarioAndComprado(usuario.get().getId(), false);
		DetallePedido detallePedido = new DetallePedido();

		/* Si no hay nada en el carrito y no se ha creado un pedido no comprado se crea uno, si no se añade detalle */
		if (!pedido.isPresent()) {
			if (juego.get().getStock() == 0) { // Se comprueba stock del juego
				ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO,
						HttpStatus.BAD_REQUEST.ordinal(), ErrorDTO.CODE_ERROR_JUEGO, "No hay más en stock",
						ErrorDTO.CODE_ERROR_JUEGO, log);
				List<ErrorDTO> errors = new ArrayList<>();
				errors.add(error);
				responseDTO.setError(errors);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
			}
			Pedido nuevoPedido = pedidoService.save(Long.valueOf(usuario.get().getId()));
			DetallePedido nuevojuego = new DetallePedido();
			nuevojuego.setCantidad(1);
			nuevojuego.setPedido(nuevoPedido);
			nuevojuego.setProducto(juego.get());
			detallePedido = detallePedidoService.save(nuevojuego);

		} else {
			Optional<DetallePedido> detalleexistente = detallePedidoService.findByPedidoAndProducto(pedido.get(),
					juego.get());
			/* Si el juego a añadir ya esta en el carrito, se comprueba que haya stock para sumarle uno */
			if (detalleexistente.isPresent()) {
				if (detalleexistente.get().getCantidad() == juego.get().getStock()) {
					ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO,
							HttpStatus.BAD_REQUEST.ordinal(), ErrorDTO.CODE_ERROR_JUEGO, "No hay más en stock",
							ErrorDTO.CODE_ERROR_JUEGO, log);
					List<ErrorDTO> errors = new ArrayList<>();
					errors.add(error);
					responseDTO.setError(errors);
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
				}
				detalleexistente.get().setCantidad(detalleexistente.get().getCantidad() + 1);
				detallePedido = detallePedidoService.save(detalleexistente.get());
			} else {
				if (juego.get().getStock() == 0) {
					ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO,
							HttpStatus.BAD_REQUEST.ordinal(), ErrorDTO.CODE_ERROR_JUEGO, "No hay más en stock",
							ErrorDTO.CODE_ERROR_JUEGO, log);
					List<ErrorDTO> errors = new ArrayList<>();
					errors.add(error);
					responseDTO.setError(errors);
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
				}
				DetallePedido nuevojuego = new DetallePedido();
				nuevojuego.setPedido(pedido.get());
				nuevojuego.setProducto(juego.get());
				nuevojuego.setCantidad(1);
				detallePedido = detallePedidoService.save(nuevojuego);
			}

		}
		responseDTO.setData(detallePedido);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("eliminarcarrito/{idusuario}/{idjuego}")
	public ResponseEntity<ResponseDTO> eliminarCarrito(@PathVariable int idusuario, @PathVariable int idjuego) {

		ResponseDTO responseDTO = new ResponseDTO();

		Optional<Usuario> usuario = usuarioService.findById(Long.valueOf(idusuario));

		if (!usuario.isPresent()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "No existe el usuario que estas buscando", ErrorDTO.CODE_ERROR_JUEGO,
					log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
		}

		Optional<Pedido> pedido = pedidoService.findByIdUsuarioAndComprado(usuario.get().getId(), false);

		if (!pedido.isPresent()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "No hay nada en tu carrito todavía", ErrorDTO.CODE_ERROR_JUEGO, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
		}

		Optional<Producto> juego = juegoService.findById(Long.valueOf(idjuego));

		if (!juego.isPresent()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "No existe el juego que se intenta quitar del carrito",
					ErrorDTO.CODE_ERROR_JUEGO, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
		}

		Optional<DetallePedido> detalle = detallePedidoService.findByPedidoAndProducto(pedido.get(), juego.get());

		if (!detalle.isPresent()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "Este juego no se encuentra en el carrito", ErrorDTO.CODE_ERROR_JUEGO,
					log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
		}

		// Si la cantidad de productos en el carrito es mayor que uno se resta uno, si no se elimina
		if (detalle.get().getCantidad() > 1) {
			detalle.get().setCantidad(detalle.get().getCantidad() - 1);
			detallePedidoService.save(detalle.get());
		} else {
			detallePedidoService.delete(detalle.get());
		}

		return ResponseEntity.ok(responseDTO);
	}

	/* Este método recoge los pedidos que se hicieron hace menos de 3 días */
	@GetMapping("comprasencamino/{idusuario}")
	public ResponseEntity<ResponseDTO> comprasEnCamino(@PathVariable int idusuario) {

		ResponseDTO responseDTO = new ResponseDTO();
		Optional<Usuario> usuario = usuarioService.findById(Long.valueOf(idusuario));

		if (!usuario.isPresent()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "No existe el usuario que estas buscando", ErrorDTO.CODE_ERROR_JUEGO,
					log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
		}

		List<Pedido> pedidos = pedidoService.pedidosEnCamino(usuario.get().getId());

		if (pedidos.isEmpty()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "No hay producto en camino", ErrorDTO.CODE_ERROR_JUEGO, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
		}

		List<DetallePedido> productos = new ArrayList<DetallePedido>();
		for (Pedido pedido : pedidos) { // Recorro todos los pedidos que cumplen la fecha
			List<DetallePedido> detallesDelPedido = detallePedidoService.findByIdPedido(pedido.getId());
			for (DetallePedido detallePedido : detallesDelPedido) {
				// Con este for meto todas las list que se creen de cada pedido en la list general
				productos.add(detallePedido);
			}
		}

		if (productos.isEmpty()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "No hay producto en camino", ErrorDTO.CODE_ERROR_JUEGO, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
		}

		responseDTO.setData(productos);
		return ResponseEntity.ok(responseDTO);
	}

	/* Este método recoge los pedidos que se hicieron hace entre 3 días y 15 */
	@GetMapping("comprasendevolucion/{idusuario}")
	public ResponseEntity<ResponseDTO> comprasEnDevolucion(@PathVariable int idusuario) {

		ResponseDTO responseDTO = new ResponseDTO();
		Optional<Usuario> usuario = usuarioService.findById(Long.valueOf(idusuario));

		if (!usuario.isPresent()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "No existe el usuario que estas buscando", ErrorDTO.CODE_ERROR_JUEGO,
					log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
		}

		List<Pedido> pedidos = pedidoService.pedidosEnDevolucion(usuario.get().getId());

		if (pedidos.isEmpty()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "No hay pedidos en fase de devolución", ErrorDTO.CODE_ERROR_JUEGO, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
		}

		List<DetallePedido> productos = new ArrayList<DetallePedido>();
		for (Pedido pedido : pedidos) {
			List<DetallePedido> detallesDelPedido = detallePedidoService.findByIdPedido(pedido.getId());
			for (DetallePedido detallePedido : detallesDelPedido) {
				// Con este for meto todas las list que se creen de cada pedido en la list general
				productos.add(detallePedido);
			}
		}

		if (productos.isEmpty()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "No hay producto en camino", ErrorDTO.CODE_ERROR_JUEGO, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
		}

		responseDTO.setData(productos);
		return ResponseEntity.ok(responseDTO);
	}

	/* Este método recoge los pedidos que se hicieron hace más de 15 días */
	@GetMapping("comprassindevolucion/{idusuario}")
	public ResponseEntity<ResponseDTO> comprasSinDevolucion(@PathVariable int idusuario) {

		ResponseDTO responseDTO = new ResponseDTO();
		Optional<Usuario> usuario = usuarioService.findById(Long.valueOf(idusuario));

		if (!usuario.isPresent()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "No existe el usuario que estas buscando", ErrorDTO.CODE_ERROR_JUEGO,
					log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
		}

		List<Pedido> pedidos = pedidoService.pedidosSinDevolucion(usuario.get().getId());

		if (pedidos.isEmpty()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "No hay producto definitivos comprados", ErrorDTO.CODE_ERROR_JUEGO, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
		}

		List<DetallePedido> productos = new ArrayList<DetallePedido>();
		for (Pedido pedido : pedidos) { // Recorro todos los pedidos que cumplen la fecha
			List<DetallePedido> detallesDelPedido = detallePedidoService.findByIdPedido(pedido.getId());
			for (DetallePedido detallePedido : detallesDelPedido) {
				// Con este for meto todas las list que se creen de cada pedido en la list general
				productos.add(detallePedido);
			}
		}

		if (productos.isEmpty()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "No hay producto en camino", ErrorDTO.CODE_ERROR_JUEGO, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
		}

		responseDTO.setData(productos);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("devolverpedido/{idpedido}/{idproducto}")
	public ResponseEntity<ResponseDTO> devolverPedido(@PathVariable int idpedido, @PathVariable int idproducto) {

		ResponseDTO responseDTO = new ResponseDTO();

		Optional<Pedido> pedido = pedidoService.findById(Long.valueOf(idpedido));

		if (!pedido.isPresent()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "No hay nada en tu carrito todavía", ErrorDTO.CODE_ERROR_JUEGO, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
		}

		Optional<Producto> juego = juegoService.findById(Long.valueOf(idproducto));

		if (!juego.isPresent()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "No existe el juego que se intenta quitar del carrito",
					ErrorDTO.CODE_ERROR_JUEGO, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
		}

		Optional<DetallePedido> detalle = detallePedidoService.findByPedidoAndProducto(pedido.get(), juego.get());

		if (!detalle.isPresent()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "Este juego no se encuentra en el carrito", ErrorDTO.CODE_ERROR_JUEGO,
					log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
		}

		detallePedidoService.devolverDetalleProducto(detalle.get()); // seteo true en el boolean devolver del detalle

		return ResponseEntity.ok(responseDTO);
	}

}
