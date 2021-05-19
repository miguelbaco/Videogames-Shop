package com.spring.microservices.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.microservices.entity.DetallePedido;
import com.spring.microservices.repository.DetallePedidoRepository;

@Service
public class DetallePedidoServiceImpl implements DetallePedidoService {
	
	@Autowired
	DetallePedidoRepository repository;
	
	public DetallePedido save(DetallePedido detallePedido) {
		return repository.save(detallePedido);
	}
}
