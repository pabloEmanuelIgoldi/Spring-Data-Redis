package com.redis.service.producto;

import java.util.List;

import com.redis.dto.ProductoResponseDTO;
import com.redis.dto.ProductoRequestDTO;

public interface IProductoService {
	
	List<ProductoResponseDTO> findAll();
	
	ProductoResponseDTO findById(Long id);
	
	ProductoResponseDTO save(ProductoRequestDTO producto);
	
	ProductoResponseDTO update(Long id, ProductoRequestDTO producto);
	
	void deleteById(Long id);
}
