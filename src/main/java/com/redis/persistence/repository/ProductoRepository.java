package com.redis.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redis.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}