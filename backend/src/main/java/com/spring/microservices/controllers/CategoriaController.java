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

import com.spring.microservices.entity.Categoria;
import com.spring.microservices.entity.dto.ErrorDTO;
import com.spring.microservices.entity.dto.ResponseDTO;
import com.spring.microservices.services.CategoriaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class CategoriaController {
	
	private static final Logger log = LoggerFactory.getLogger(CategoriaController.class);
	
	@Autowired
	CategoriaService categoriaService;
	
	@GetMapping("/categorias")
	public ResponseEntity<ResponseDTO> allCategorias() {
		
		ResponseDTO responseDTO = new ResponseDTO();
		List<Categoria> listacategorias = categoriaService.allCategorias();
		
		if(listacategorias.isEmpty()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGOS, HttpStatus.NOT_FOUND.ordinal(),
			        ErrorDTO.CODE_ERROR_JUEGOS, "No se encontraron categorias disponibles", ErrorDTO.CODE_ERROR_JUEGOS, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
		}
		responseDTO.setData(listacategorias);
		return ResponseEntity.ok(responseDTO);
	}
	
	@GetMapping("categoria/{idcategoria}")
	public ResponseEntity<ResponseDTO> juego(@PathVariable int idcategoria) {
		
		ResponseDTO responseDTO = new ResponseDTO();
		Optional<Categoria> juego = categoriaService.findById(Long.valueOf(idcategoria));
		
		if(!juego.isPresent()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
			        ErrorDTO.CODE_ERROR_JUEGO, "No existe la categoria que estas buscando", ErrorDTO.CODE_ERROR_JUEGO, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
		}
		responseDTO.setData(juego.get());
		return ResponseEntity.ok(responseDTO);
	}
}
