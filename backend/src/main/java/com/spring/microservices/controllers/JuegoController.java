package com.spring.microservices.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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

import com.spring.microservices.entity.Producto;
import com.spring.microservices.entity.Valoracion;
import com.spring.microservices.entity.dto.ErrorDTO;
import com.spring.microservices.entity.dto.ResponseDTO;
import com.spring.microservices.entity.dto.ValoracionDTO;
import com.spring.microservices.services.JuegoService;
import com.spring.microservices.utils.BackendUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class JuegoController {

	private static final Logger log = LoggerFactory.getLogger(JuegoController.class);

	@Autowired
	JuegoService juegoService;

	@Autowired
	BackendUtils backendUtils;

	@GetMapping("/juegos/{aleatorio}")
	public ResponseEntity<ResponseDTO> allJuegos(@PathVariable boolean aleatorio) {

		ResponseDTO responseDTO = new ResponseDTO();
		List<Producto> listajuegos = juegoService.allJuegos();

		if (listajuegos.isEmpty()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGOS, HttpStatus.NOT_FOUND.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGOS, "No se encontraron juegos disponibles", ErrorDTO.CODE_ERROR_JUEGOS,
					log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
		}

		Iterator<Producto> iterator = listajuegos.iterator();

		/* Aqui recorro la lista para solo recoger los productos con stock */
		while (iterator.hasNext()) {
			Producto producto = iterator.next();
			if (producto.getStock() == 0) {
				iterator.remove();
			}
		}

		if (aleatorio) {
			Collections.shuffle(listajuegos); // Para mostrar juegos de forma aleatoria
		} else {
			// El orden se invierte para que los modificados ultimamente (más vendidos), salgan los primeros
			Collections.reverse(listajuegos);
		}

		responseDTO.setData(listajuegos);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("juego/{idjuego}")
	public ResponseEntity<ResponseDTO> juego(@PathVariable int idjuego) {

		ResponseDTO responseDTO = new ResponseDTO();
		Optional<Producto> juego = juegoService.findById(Long.valueOf(idjuego));

		if (!juego.isPresent()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "No existe el juego que estas buscando", ErrorDTO.CODE_ERROR_JUEGO, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
		}
		responseDTO.setData(juego.get());
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/juegosporcategoria/{idcategoria}")
	public ResponseEntity<ResponseDTO> juegosByCategoria(@PathVariable int idcategoria) {

		ResponseDTO responseDTO = new ResponseDTO();
		List<Producto> listajuegos = juegoService.juegosByCategoria(idcategoria);

		if (listajuegos.isEmpty()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGOS, HttpStatus.NOT_FOUND.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGOS, "No se encontraron juegos disponibles", ErrorDTO.CODE_ERROR_JUEGOS,
					log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
		}
		responseDTO.setData(listajuegos);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/valoraciones/{idjuego}")
	public ResponseEntity<ResponseDTO> valoracionesJuego(@PathVariable int idjuego) {

		ResponseDTO responseDTO = new ResponseDTO();
		Optional<Producto> juego = juegoService.findById(Long.valueOf(idjuego));

		if (!juego.isPresent()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "No existe el juego que estas buscando", ErrorDTO.CODE_ERROR_JUEGO, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
		}

		List<Valoracion> valoraciones = juegoService.valoracionesJuego(juego.get());

		if (valoraciones.isEmpty()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "No hay valoraciones para este juego aún, sé el primero",
					ErrorDTO.CODE_ERROR_JUEGO, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
		}

		responseDTO.setData(valoraciones);
		return ResponseEntity.ok(responseDTO);
	}

	@PostMapping("/anadirvaloracion")
	public ResponseEntity<ResponseDTO> anadirValoracion(@RequestBody ValoracionDTO valoracionDTO) {

		ResponseDTO responseDTO = new ResponseDTO();

		/* Convierto el DTO en una entidad JPA */
		Valoracion valoracion = backendUtils.valoracionDTOtoValoracion(valoracionDTO);

		if (valoracion == null) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "El juego o usuario de la valoración no existe",
					ErrorDTO.CODE_ERROR_JUEGO, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
		}

		Valoracion nueva = juegoService.saveValoracion(valoracion);
		responseDTO.setData(nueva);
		return ResponseEntity.ok(responseDTO);
	}

}
