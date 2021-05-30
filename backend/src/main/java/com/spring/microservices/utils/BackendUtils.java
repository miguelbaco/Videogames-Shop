package com.spring.microservices.utils;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.microservices.entity.Categoria;
import com.spring.microservices.entity.Producto;
import com.spring.microservices.entity.Usuario;
import com.spring.microservices.entity.Valoracion;
import com.spring.microservices.entity.dto.CategoriaDTO;
import com.spring.microservices.entity.dto.ProductoDTO;
import com.spring.microservices.entity.dto.UsuarioDTO;
import com.spring.microservices.entity.dto.ValoracionDTO;
import com.spring.microservices.services.JuegoService;
import com.spring.microservices.services.UsuarioService;

@Component
public class BackendUtils {

	@Autowired
	JuegoService juegoService;

	@Autowired
	UsuarioService usuarioService;

	public Usuario usuarioDTOtoUsuario(UsuarioDTO usuarioDTO) {
		Usuario usuario = new Usuario();
		usuario.setNombre(usuarioDTO.getNombre());
		usuario.setApellidos(usuarioDTO.getApellidos());
		usuario.setDireccion(usuarioDTO.getDireccion());
		usuario.setEmail(usuarioDTO.getEmail());
		usuario.setPassword(usuarioDTO.getPassword());
		if (usuarioDTO.isAdmin()) {
			usuario.setAdmin(usuarioDTO.isAdmin());
		}
		return usuario;
	}

	public Producto productoDTOtoProducto(ProductoDTO productoDTO) {

		Producto producto = new Producto();
		producto.setIdcategoria(Long.valueOf(productoDTO.getIdcategoria()));
		producto.setDescripcion(productoDTO.getDescripcion());
		producto.setImagen(productoDTO.getImagen());
		producto.setNombre(productoDTO.getNombre());
		producto.setPrecio(productoDTO.getPrecio());
		producto.setStock(productoDTO.getStock());
		return producto;
	}

	public Valoracion valoracionDTOtoValoracion(ValoracionDTO valoracionDTO) {
		Valoracion valoracion = new Valoracion();
		valoracion.setComentario(valoracionDTO.getComentario());
		valoracion.setPuntuacion(valoracionDTO.getPuntuacion());

		Optional<Producto> juego = juegoService.findById(Long.valueOf(valoracionDTO.getProducto().getId()));
		if (!juego.isPresent()) {
			return null;
		}
		valoracion.setProducto(juego.get());

		Optional<Usuario> usuario = usuarioService.findById(Long.valueOf(valoracionDTO.getUsuario().getId()));
		if (!usuario.isPresent()) {
			return null;
		}
		valoracion.setUsuario(usuario.get());

		return valoracion;
	}

	public Categoria categoriaDTOtoCategoria(CategoriaDTO categoriaDTO) {
		Categoria categoria = new Categoria();
		categoria.setDescripcion(categoriaDTO.getDescripcion());
		categoria.setNombre(categoriaDTO.getNombre());
		return categoria;
	}

}
