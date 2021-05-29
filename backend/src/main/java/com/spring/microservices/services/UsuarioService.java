package com.spring.microservices.services;

import java.util.List;
import java.util.Optional;
import com.spring.microservices.entity.Usuario;
import com.spring.microservices.entity.dto.UsuarioDTO;

public interface UsuarioService {
	
	public List<Usuario> allUsuarios();
	
	public Usuario save(Usuario producto);

	public Optional<Usuario> findById(Long id);
	
	public Optional<Usuario> findByEmail(String email);

	public void updateUsuario(UsuarioDTO usuarioDTO);
}
