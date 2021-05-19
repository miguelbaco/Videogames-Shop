package com.spring.microservices.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.microservices.entity.Pedido;
import com.spring.microservices.repository.PedidoRepository;

@Service
public class PedidoServiceImpl implements PedidoService {
	
	@Autowired
	PedidoRepository repository;

	public Optional<Pedido> findByIdUsuarioAndComprado(Long idUsuario, boolean comprado) {
		
		return repository.findByIdUsuarioAndComprado(idUsuario, comprado);
	}
	
	public Pedido save(Long idUsuario) {
		
		Pedido pedido = new Pedido();
		pedido.setIdUsuario(idUsuario);
		LocalDate date = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String fecha = date.format(formatter); 
		pedido.setFecha(fecha);
		return repository.save(pedido);
	}
}
