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

import com.spring.microservices.entity.Pedido;
import com.spring.microservices.entity.Usuario;
import com.spring.microservices.entity.dto.ErrorDTO;
import com.spring.microservices.entity.dto.ResponseDTO;
import com.spring.microservices.services.PedidoService;
import com.spring.microservices.services.UsuarioService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class PedidoController {
	
	private static final Logger log = LoggerFactory.getLogger(PedidoController.class);
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	PedidoService pedidoService;
	
	@GetMapping("pedido/{idusuario}")
	public ResponseEntity<ResponseDTO> pedidoActual(@PathVariable int idusuario) {
		
		ResponseDTO responseDTO = new ResponseDTO();
		
		Optional<Usuario> usuario = usuarioService.findById(Long.valueOf(idusuario));
		
		if(!usuario.isPresent()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
			        ErrorDTO.CODE_ERROR_JUEGO, "No existe el usuario que estas buscando", ErrorDTO.CODE_ERROR_JUEGO, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
		}
		
		Optional<Pedido> pedido = pedidoService.findByIdUsuarioAndComprado(usuario.get().getId(), false);
		
		if(!pedido.isPresent()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
			        ErrorDTO.CODE_ERROR_JUEGO, "Aun no tienes nada en el carrito", ErrorDTO.CODE_ERROR_JUEGO, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
		}
		
		responseDTO.setData(pedido.get());
		return ResponseEntity.ok(responseDTO);
	}
	
	
	public Pedido crearPedido(Long idUsuario) {
		return pedidoService.save(idUsuario);
	}
}
