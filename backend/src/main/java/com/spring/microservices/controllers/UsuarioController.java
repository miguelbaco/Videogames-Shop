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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.spring.microservices.entity.Usuario;
import com.spring.microservices.entity.dto.ErrorDTO;
import com.spring.microservices.entity.dto.ResponseDTO;
import com.spring.microservices.entity.dto.UsuarioDTO;
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
		List<Usuario> listausuarios = usuarioService.allUsuarios();
		
		if(listausuarios.isEmpty()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGOS, HttpStatus.NOT_FOUND.ordinal(),
			        ErrorDTO.CODE_ERROR_JUEGOS, "No se encontraron usuarios disponibles", ErrorDTO.CODE_ERROR_JUEGOS, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
		}
		responseDTO.setData(listausuarios);
		return ResponseEntity.ok(responseDTO);
	}
	
	@GetMapping("/usuario/{idusuario}")
	public ResponseEntity<ResponseDTO> encontrarUsuario(@PathVariable int idusuario) {
		
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
		responseDTO.setData(usuario.get());
		return ResponseEntity.ok(responseDTO);
	}
	
	@PostMapping("/loginusuario")
	public ResponseEntity<ResponseDTO> loginUsuario(@RequestBody UsuarioDTO usuarioDTO) {
		
		ResponseDTO responseDTO = new ResponseDTO();
		Optional<Usuario> usuario = usuarioService.findByEmail(usuarioDTO.getEmail());
		
		if(!usuario.isPresent()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
			        ErrorDTO.CODE_ERROR_JUEGO, "No hay ningún usuario registrado con ese email", ErrorDTO.CODE_ERROR_JUEGO, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDTO);
		}
		
		if(!usuarioDTO.getPassword().equals(usuario.get().getPassword())) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
			        ErrorDTO.CODE_ERROR_JUEGO, "El correo y la contraseña no coinciden", ErrorDTO.CODE_ERROR_JUEGO, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDTO);			
		}
		responseDTO.setData(usuario);		
		return ResponseEntity.ok(responseDTO);
	}
	
	@PostMapping("/registrarusuario")
	public ResponseEntity<ResponseDTO> registrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
		ResponseDTO responseDTO = new ResponseDTO();
		
		Optional<Usuario> usuarioyaregistrado = usuarioService.findByEmail(usuarioDTO.getEmail());
		
		if(usuarioyaregistrado.isPresent()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
			        ErrorDTO.CODE_ERROR_JUEGO, "Ya existe un usuario con ese correo electrónico", ErrorDTO.CODE_ERROR_JUEGO, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDTO);
		}
		
		Usuario nuevoUsuario = 	usuarioService.usuarioDTOtoUsuario(usuarioDTO);
		Usuario usregistrado = usuarioService.save(nuevoUsuario);
		responseDTO.setData(usregistrado);
		return ResponseEntity.ok(responseDTO);
	}
}
