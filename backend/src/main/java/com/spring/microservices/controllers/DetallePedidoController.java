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
	
	@GetMapping("anadircarrito/{idusuario}/{idjuego}/{cantidad}")
	public ResponseEntity<ResponseDTO> anadirCarrito(@PathVariable int idusuario, @PathVariable int idjuego, @PathVariable int cantidad) {
		
		ResponseDTO responseDTO = new ResponseDTO();
		
			Optional<Usuario> usuario = usuarioService.findById(Long.valueOf(idusuario));
    		Optional<Producto> juego = juegoService.findById(Long.valueOf(idjuego));
    		
    		if(!usuario.isPresent()) {
    			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
    			        ErrorDTO.CODE_ERROR_JUEGO, "No existe el usuario que estas buscando", ErrorDTO.CODE_ERROR_JUEGO, log);
    			List<ErrorDTO> errors = new ArrayList<>();
    			errors.add(error);
    			responseDTO.setError(errors);
    			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
    		} else if(!juego.isPresent()) {
    			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
    			        ErrorDTO.CODE_ERROR_JUEGO, "No existe el juego que estas buscando", ErrorDTO.CODE_ERROR_JUEGO, log);
    			List<ErrorDTO> errors = new ArrayList<>();
    			errors.add(error);
    			responseDTO.setError(errors);
    			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
    		}
    		
    		Optional<Pedido> pedido = pedidoService.findByIdUsuarioAndComprado(usuario.get().getId(), false);
    		DetallePedido detallePedido  = new DetallePedido();
    		
    		if(!pedido.isPresent()) {
        		Pedido nuevoPedido = pedidoService.save(Long.valueOf(usuario.get().getId()));
        		
        		DetallePedido nuevojuego = new DetallePedido();
        		nuevojuego.setCantidad(cantidad);
        		nuevojuego.setPedido(nuevoPedido);
        		nuevojuego.setProducto(juego.get());
        		detallePedido = detallePedidoService.save(nuevojuego);
        		
    		} else {
    			DetallePedido nuevojuego = new DetallePedido();
        		nuevojuego.setCantidad(cantidad);
        		nuevojuego.setPedido(pedido.get());
        		nuevojuego.setProducto(juego.get());
        		detallePedido = detallePedidoService.save(nuevojuego);
    			
    		}
    		responseDTO.setData(detallePedido);
    		return ResponseEntity.ok(responseDTO);
	}
}
