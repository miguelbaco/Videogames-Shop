package com.spring.microservices.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.microservices.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{
	
	public Optional<Pedido> findByIdUsuarioAndComprado(Long idUsuario, boolean comprado);
	
	public List<Pedido> findByIdUsuario(Long idUsuario);
}