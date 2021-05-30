package com.spring.microservices.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.spring.microservices.entity.Categoria;
import com.spring.microservices.entity.Producto;
import com.spring.microservices.entity.Usuario;
import com.spring.microservices.entity.dto.CategoriaDTO;
import com.spring.microservices.entity.dto.ErrorDTO;
import com.spring.microservices.entity.dto.ProductoDTO;
import com.spring.microservices.entity.dto.ResponseDTO;
import com.spring.microservices.entity.dto.UsuarioDTO;
import com.spring.microservices.services.CategoriaService;
import com.spring.microservices.services.JuegoService;
import com.spring.microservices.services.UsuarioService;
import com.spring.microservices.utils.BackendUtils;

@Controller
public class AdminController {

	private static final Logger log = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	JuegoService juegoService;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	CategoriaService categoriaService;

	@Autowired
	BackendUtils backendUtils;

	@GetMapping("/adminjuegos")
	public ResponseEntity<ResponseDTO> allJuegos() {

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

		responseDTO.setData(listajuegos);
		return ResponseEntity.ok(responseDTO);
	}

	@PostMapping("/adminanadirjuego/{idusuario}")
	public ResponseEntity<ResponseDTO> nuevojuego(@PathVariable int idusuario, @RequestBody ProductoDTO productoDTO) {

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

		if (usuario.get().isAdmin()) { // Así compruebo que el usuario que realiza la acción es un administrador
			// Aquí pasaría el objeto con estructura DTO a una entidad para poder guardarlo
			juegoService.save(backendUtils.productoDTOtoProducto(productoDTO)); 
			return ResponseEntity.ok(responseDTO);
		} else {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.FORBIDDEN.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "No tienes autorizacion para realizar esa acción",
					ErrorDTO.CODE_ERROR_JUEGO, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseDTO);
		}
	}

	@PostMapping("/admineliminarjuego/{idusuario}")
	public ResponseEntity<ResponseDTO> borrarjuego(@PathVariable int idusuario, @RequestBody ProductoDTO productoDTO) {

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

		if (usuario.get().isAdmin()) { // Así compruebo que el usuario que realiza la acción es un administrador
			Optional<Producto> producto = juegoService.findById(Long.valueOf(productoDTO.getId()));

			if (!producto.isPresent()) {
				ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
						ErrorDTO.CODE_ERROR_JUEGO, "No existe el juego que quieres borrar", ErrorDTO.CODE_ERROR_JUEGO,
						log);
				List<ErrorDTO> errors = new ArrayList<>();
				errors.add(error);
				responseDTO.setError(errors);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
			}

			juegoService.deleteJuego(producto.get());

			return ResponseEntity.ok(responseDTO);
		} else {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.FORBIDDEN.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "No tienes autorizacion para realizar esa acción",
					ErrorDTO.CODE_ERROR_JUEGO, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseDTO);
		}
	}

	@PostMapping("/admineditarjuego/{idusuario}")
	public ResponseEntity<ResponseDTO> editarjuego(@PathVariable int idusuario, @RequestBody ProductoDTO productoDTO) {

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

		if (usuario.get().isAdmin()) { // Así compruebo que el usuario que realiza la acción es un administrador
			Optional<Producto> producto = juegoService.findById(Long.valueOf(productoDTO.getId()));

			if (!producto.isPresent()) {
				ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
						ErrorDTO.CODE_ERROR_JUEGO, "No existe el juego que quieres borrar", ErrorDTO.CODE_ERROR_JUEGO,
						log);
				List<ErrorDTO> errors = new ArrayList<>();
				errors.add(error);
				responseDTO.setError(errors);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
			}

			juegoService.updateJuego(productoDTO);
			return ResponseEntity.ok(responseDTO);
		} else {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.FORBIDDEN.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "No tienes autorizacion para realizar esa acción",
					ErrorDTO.CODE_ERROR_JUEGO, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseDTO);
		}
	}

	@PostMapping("/admineditarcategoria/{idusuario}")
	public ResponseEntity<ResponseDTO> editarcategoria(@PathVariable int idusuario,
			@RequestBody CategoriaDTO categoriaDTO) {

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

		if (usuario.get().isAdmin()) { // Así compruebo que el usuario que realiza la acción es un administrador
			// Aqui solo compruebo que exista la categoria
			Optional<Categoria> categoria = categoriaService.findById(Long.valueOf(categoriaDTO.getId()));
			if (!categoria.isPresent()) {
				ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
						ErrorDTO.CODE_ERROR_JUEGO, "No existe la categoria que quieres modificar",
						ErrorDTO.CODE_ERROR_JUEGO, log);
				List<ErrorDTO> errors = new ArrayList<>();
				errors.add(error);
				responseDTO.setError(errors);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
			}

			categoriaService.updateCategoria(categoriaDTO);
			return ResponseEntity.ok(responseDTO);
		} else {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.FORBIDDEN.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "No tienes autorizacion para realizar esa acción",
					ErrorDTO.CODE_ERROR_JUEGO, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseDTO);
		}
	}

	@PostMapping("/adminanadircategoria/{idusuario}")
	public ResponseEntity<ResponseDTO> nuevaCategoria(@PathVariable int idusuario,
			@RequestBody CategoriaDTO categoriaDTO) {

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

		if (usuario.get().isAdmin()) { // Así compruebo que el usuario que realiza la acción es un administrador
			// Pasaría del objeto de DTO a la entidad para añadirla
			categoriaService.save(backendUtils.categoriaDTOtoCategoria(categoriaDTO));
			return ResponseEntity.ok(responseDTO);
		} else {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.FORBIDDEN.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "No tienes autorizacion para realizar esa acción",
					ErrorDTO.CODE_ERROR_JUEGO, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseDTO);
		}
	}

	@PostMapping("/admineditarusuario/{idusuario}")
	public ResponseEntity<ResponseDTO> editarusuario(@PathVariable int idusuario, @RequestBody UsuarioDTO usuarioDTO) {

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

		if (usuario.get().isAdmin()) { // Así compruebo que el usuario que realiza la acción es un administrador
			Optional<Usuario> editousuario = usuarioService.findById(Long.valueOf(usuarioDTO.getId()));

			if (!editousuario.isPresent()) {
				ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
						ErrorDTO.CODE_ERROR_JUEGO, "No existe el usuario a editar", ErrorDTO.CODE_ERROR_JUEGO, log);
				List<ErrorDTO> errors = new ArrayList<>();
				errors.add(error);
				responseDTO.setError(errors);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
			}

			usuarioService.updateUsuario(usuarioDTO);
			return ResponseEntity.ok(responseDTO);

		} else {
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.FORBIDDEN.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "No tienes autorizacion para realizar esa acción",
					ErrorDTO.CODE_ERROR_JUEGO, log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseDTO);
		}
	}

	/*
	 * El método siguiente es el que se encarga de subir la imagen a la carpeta
	 * assets de donde se coge la imagen para mostrarla en la web
	 */

	@PostMapping("/subirimagen")
	public ResponseEntity<ResponseDTO> uploadFile(@RequestParam("file") MultipartFile file) {
		// MultipartFile es el objeto java que permite cualquier archivo
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			juegoService.subirImagen(file); // Este es el método que se encargará de guardarlo
		} catch (FileUploadException e) { // Aquí se recoge la excepcion con el motivo por el cual no es posible guardar la imagen
			ErrorDTO error = ErrorDTO.creaErrorLogger(ErrorDTO.CODE_ERROR_JUEGO, HttpStatus.BAD_REQUEST.ordinal(),
					ErrorDTO.CODE_ERROR_JUEGO, "No existe el usuario que estas buscando", ErrorDTO.CODE_ERROR_JUEGO,
					log);
			List<ErrorDTO> errors = new ArrayList<>();
			errors.add(error);
			responseDTO.setError(errors);
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
		}
		return ResponseEntity.ok(responseDTO);
	}

}
