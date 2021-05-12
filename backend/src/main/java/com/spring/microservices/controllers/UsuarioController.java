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

import com.spring.microservices.entity.ErrorDTO;
import com.spring.microservices.entity.ResponseDTO;
import com.spring.microservices.entity.Usuario;
import com.spring.microservices.services.UsuarioService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class UsuarioController {
	
	private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);
	
	@Autowired
	UsuarioService usuarioService;
	
	@GetMapping("/usuarios")
	public ResponseEntity<ResponseDTO> allJuegos() {
		
		ResponseDTO responseDTO = new ResponseDTO();
		List<Usuario> listajuegos = usuarioService.allUsuarios();
		
		if(listajuegos.isEmpty()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGOS, HttpStatus.NOT_FOUND.ordinal(),
			        ErrorDTO.CODE_ERROR_JUEGOS, "No se encontraron usuarios disponibles", ErrorDTO.CODE_ERROR_JUEGOS, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
		}
		responseDTO.setData(listajuegos);
		return ResponseEntity.ok(responseDTO);
	}
	
	@GetMapping("usuario/{idusuario}")
	public ResponseEntity<ResponseDTO> juego(@PathVariable int idusuario) {
		
		ResponseDTO responseDTO = new ResponseDTO();
		Optional<Usuario> juego = usuarioService.findById(Long.valueOf(idusuario));
		
		if(!juego.isPresent()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
			        ErrorDTO.CODE_ERROR_JUEGO, "No existe el usuario que estas buscando", ErrorDTO.CODE_ERROR_JUEGO, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
		}
		responseDTO.setData(juego.get());
		return ResponseEntity.ok(responseDTO);
	}
}
