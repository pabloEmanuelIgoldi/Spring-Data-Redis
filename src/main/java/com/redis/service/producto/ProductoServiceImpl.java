package com.redis.service.producto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.redis.dto.ProductoResponseDTO;
import com.redis.dto.ProductoRequestDTO;
import com.redis.entity.Producto;
import com.redis.persistence.dao.IProductoDao;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductoServiceImpl implements IProductoService {
	
	private final IProductoDao iProductoDao;

    public ProductoServiceImpl(IProductoDao iProductoDao) {
		this.iProductoDao = iProductoDao;
	}

	@Override
	public List<ProductoResponseDTO> findAll() {
		log.info("INGRESO A ProductoServiceImpl.findAll().");
		return this.mapperList(iProductoDao.findAll());
	}

	private List<ProductoResponseDTO> mapperList(List<Producto> list) {
		return list.stream()
				   .map(p -> new ProductoResponseDTO(p.getId(), p.getNombre(), p.getFecha(), p.getPrecio()))
				   .collect(Collectors.toList());
	}

	@Override
	public ProductoResponseDTO findById(Long id) {
		log.info("INGRESO A ProductoServiceImpl.findById(). ID: " + id);
		return this.mapperProductoDTO(this.iProductoDao.findById(id));
	}

	@Override
	public ProductoResponseDTO save(ProductoRequestDTO request) {
		log.info("INGRESO A ProductoServiceImpl.save().");
		Producto newProducto = Producto.builder()
				   					   .fecha(request.getFecha())
				   					   .nombre(request.getNombre())
				   					   .precio(request.getPrecio())
				   					   .build();		
		Producto savedProducto = this.iProductoDao.save(newProducto);
		return this.mapperProductoDTO(savedProducto);
	}

	@Override
	public ProductoResponseDTO update(Long id, ProductoRequestDTO request) {
		log.info("INGRESO A ProductoServiceImpl.update().");
		
		Producto updateProducto = Producto.builder()
										  .id(id)
										  .fecha(request.getFecha())
										  .nombre(request.getNombre())
										  .precio(request.getPrecio())
										  .build();
		Producto savedProducto = this.iProductoDao.save(updateProducto);
		return this.mapperProductoDTO(savedProducto);
	}

	@Override
	public void deleteById(Long id) {
		log.info("INGRESO A ProductoServiceImpl.deleteById(). ID: "+id);
		this.iProductoDao.deleteById(id);
	}
	
	private ProductoResponseDTO mapperProductoDTO(Producto p) {
		return new ProductoResponseDTO(p.getId(), p.getNombre(), p.getFecha(), p.getPrecio());
		       
	}
}
