package com.spring.microservices.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.microservices.entity.DetallePedido;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long>{
}