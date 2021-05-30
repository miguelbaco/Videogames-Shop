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

import com.spring.microservices.entity.Deseo;
import com.spring.microservices.entity.Producto;
import com.spring.microservices.entity.Usuario;
import com.spring.microservices.entity.dto.ErrorDTO;
import com.spring.microservices.entity.dto.ResponseDTO;
import com.spring.microservices.services.DeseoService;
import com.spring.microservices.services.JuegoService;
import com.spring.microservices.services.UsuarioService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class DeseoController {

	private static final Logger log = LoggerFactory.getLogger(DeseoController.class);

	@Autowired
	DeseoService deseoService;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	JuegoService juegoService;

	@GetMapping("deseos/{idusuario}")
	public ResponseEntity<ResponseDTO> allDeseos(@PathVariable int idusuario) {

		ResponseDTO responseDTO = new ResponseDTO();
		List<Deseo> listadeseos = deseoService.findByIdUsuario(Long.valueOf(idusuario));

		if (listadeseos.isEmpty()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "No existe el juego que estas buscando", ErrorDTO.CODE_ERROR_JUEGO, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
		}
		responseDTO.setData(listadeseos);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("anadirdeseo/{idusuario}/{idjuego}")
	public ResponseEntity<ResponseDTO> anadirDeseo(@PathVariable int idusuario, @PathVariable int idjuego) {

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

		Optional<Deseo> yaexistedeseo = deseoService.findByUsuarioAndProducto(usuario.get(), juego.get());
		/* Aquí si no existe el deseo creo uno nuevo y si ya existe lo notifico con un error y es mostrado en el cliente  */
		if (!yaexistedeseo.isPresent()) {
			Deseo deseo = new Deseo();
			deseo.setUsuario(usuario.get());
			deseo.setProducto(juego.get());

			Deseo deseoregistrado = deseoService.save(deseo);

			responseDTO.setData(deseoregistrado);
			return ResponseEntity.ok(responseDTO);
		} else {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "El juego ya esta en tu lista", ErrorDTO.CODE_ERROR_JUEGO, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
		}
	}

	@GetMapping("eliminardeseo/{idusuario}/{idjuego}")
	public ResponseEntity<ResponseDTO> eliminarDeseo(@PathVariable int idusuario, @PathVariable int idjuego) {
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

		Optional<Deseo> yaexistedeseo = deseoService.findByUsuarioAndProducto(usuario.get(), juego.get());

		if (!yaexistedeseo.isPresent()) {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "El juego ya no esta en tu lista", ErrorDTO.CODE_ERROR_JUEGO, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
		} else {
			try {
				deseoService.delete(yaexistedeseo.get());
			} catch (Exception e) {
				ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
						ErrorDTO.CODE_ERROR_JUEGO, "No se ha podido borrar el juego de la BD",
						ErrorDTO.CODE_ERROR_JUEGO, log);
				List<ErrorDTO> errors = new ArrayList<>();
				errors.add(error);
				responseDTO.setError(errors);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
			}
			return ResponseEntity.ok(responseDTO);
		}
	}

}
