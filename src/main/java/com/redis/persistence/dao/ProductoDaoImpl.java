package com.redis.persistence.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import com.redis.entity.Producto;
import com.redis.exception.EntityNotFoundException;
import com.redis.persistence.repository.ProductoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@CacheConfig(cacheNames = "productos")
public class ProductoDaoImpl implements IProductoDao {
	
	private static final String ALL_PRODUCTS_KEY = "'allProducts'";
    private final ProductoRepository productoRepository;        

	public ProductoDaoImpl(ProductoRepository productoRepository) {
		this.productoRepository = productoRepository;
	}

	@Cacheable(key = "#id", unless = "#result == null")
	@Override
	public Producto findById(Long id) {
		log.info("INGRESO A ProductoDaoImpl.findById(). ID: " +id);
		Optional<Producto> optionalProducto = this.productoRepository.findById(id);
		if (optionalProducto.isPresent()) {
			return optionalProducto.get();
		} else {
			log.error("ERROR EN ProductoDaoImpl.findById(). Producto no encontrado con id: " + id);
			throw new EntityNotFoundException("Producto no encontrado con id: " + id);
		}
	}

	
	@Cacheable(key = ALL_PRODUCTS_KEY, sync = true)
	@Override
	public List<Producto> findAll() {
		log.info("INGRESO A ProductoDaoImpl.findAll().");
		return this.productoRepository.findAll();
	}

	@Caching(put = @CachePut(key = "#result.id", unless = "#result == null"),
		        evict = @CacheEvict(key = ALL_PRODUCTS_KEY)
		     )	
	@Override
	public Producto save(Producto producto) {
		log.info("INGRESO A ProductoDaoImpl.save().");
		return this.productoRepository.save(producto);
	}

	@Caching(
	        put = @CachePut(key = "#id", unless = "#result == null"),
	        evict = @CacheEvict(key = ALL_PRODUCTS_KEY)
			)
	@Override
	public Producto update(Producto producto) {
		log.info("INGRESO A ProductoDaoImpl.update().");
		Optional<Producto> optionalProducto = this.productoRepository.findById(producto.getId());
		if (optionalProducto.isPresent()) {
			return this.productoRepository.save(producto);			
		} else {
			log.error("ERROR EN ProductoDaoImpl.update(). Producto no encontrado con id: " + producto.getId());
			throw new EntityNotFoundException("Producto no encontrado con id: " + producto.getId());
		}	
	}

	@Caching(
	        evict = {@CacheEvict(key = "#id"),@CacheEvict(key = ALL_PRODUCTS_KEY)}
		    )
	@Override
	public void deleteById(Long id) {
		log.info("INGRESO A ProductoDaoImpl.deleteById(). ID : " + id);
		Optional<Producto> optionalProducto = this.productoRepository.findById(id);
		if (optionalProducto.isPresent()) {
			this.productoRepository.deleteById(id);
		} else {
			log.error("ERROR EN ProductoDaoImpl.deleteById(). Producto no encontrado con id: " + id);
			throw new EntityNotFoundException("Producto no encontrado con id: " + id);
		}		
	}

}
