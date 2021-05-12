package com.spring.microservices.services;

import java.util.List;
import java.util.Optional;
import com.spring.microservices.entity.Usuario;

public interface UsuarioService {
	
	public List<Usuario> allUsuarios();
	
	public Usuario save(Usuario producto);

	public Optional<Usuario> findById(Long id);
}
