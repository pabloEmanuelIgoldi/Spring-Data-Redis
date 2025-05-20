package com.redis.persistence.dao;

import java.util.List;

import com.redis.entity.Producto;

public interface IProductoDao {

	Producto findById(Long id);
	
    List<Producto> findAll();
    
    Producto save(Producto producto);
    
    Producto update(Producto producto);
    
    void deleteById(Long id);
}
