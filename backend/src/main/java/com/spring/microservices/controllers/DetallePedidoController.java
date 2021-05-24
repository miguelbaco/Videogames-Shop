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

		List<DetallePedido> detalles = detallePedidoService.findByIdPedido(pedido.get().getId());

		List<Producto> productos = new ArrayList<Producto>();
		for (DetallePedido detalle : detalles) {
			for (int i = 0; i < detalle.getCantidad(); i++) {
				productos.add(detalle.getProducto());
			}
		}

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

		pedidoService.realizarCompra(pedido.get());
		
		return ResponseEntity.ok(responseDTO);
	}
	
	@GetMapping("juegoscomprados/{idusuario}")
	public ResponseEntity<ResponseDTO> comprasRealizadas(@PathVariable int idusuario) {

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

		List<Pedido> pedidos = pedidoService.pedidosRealizados(usuario.get().getId());

		if (pedidos.isEmpty()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
			        ErrorDTO.CODE_ERROR_JUEGO, "No has comprado nada todavia", ErrorDTO.CODE_ERROR_JUEGO, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
		}

		List<Producto> productos = detallePedidoService.productosComprados(pedidos);

		responseDTO.setData(productos);
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

		if (!pedido.isPresent()) {
			Pedido nuevoPedido = pedidoService.save(Long.valueOf(usuario.get().getId()));

			DetallePedido nuevojuego = new DetallePedido();
			nuevojuego.setCantidad(1);
			nuevojuego.setPedido(nuevoPedido);
			nuevojuego.setProducto(juego.get());
			detallePedido = detallePedidoService.save(nuevojuego);

		} else {
			Optional<DetallePedido> detalleexistente = detallePedidoService.findByPedidoAndProducto(pedido.get(),
			        juego.get());

			if (detalleexistente.isPresent()) {
				detalleexistente.get().setCantidad(detalleexistente.get().getCantidad() + 1);
				detallePedido = detallePedidoService.save(detalleexistente.get());
			} else {
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

		if (detalle.get().getCantidad() > 1) {
			detalle.get().setCantidad(detalle.get().getCantidad() - 1);
			detallePedidoService.save(detalle.get());
		} else {
			detallePedidoService.delete(detalle.get());
		}

		return ResponseEntity.ok(responseDTO);
	}
}
