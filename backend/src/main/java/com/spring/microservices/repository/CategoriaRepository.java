package com.spring.microservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.microservices.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}