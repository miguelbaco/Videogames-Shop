package com.spring.microservices.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.spring.microservices.entity.ResponseDTO;

@Controller
public class JuegosController {
	
	@GetMapping("/juegos")
	public ResponseEntity<ResponseDTO> allJuegos() {
		
		
		return null;
	}
	
	@GetMapping("juego/{idjuego}")
	public ResponseEntity<ResponseDTO> juego(@PathVariable int idjuego) {
		
		
		return null;
	}
}
