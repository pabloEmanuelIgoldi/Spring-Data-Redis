package com.redis.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redis.dto.ApiResponseDTO;
import com.redis.dto.ProductoRequestDTO;
import com.redis.dto.ProductoResponseDTO;
import com.redis.service.producto.IProductoService;
import com.redis.util.CodeResponseUtil;
import com.redis.util.MensajeResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/productos")
@Tag(name = "Producto", description = "Endpoints para gestionar productos.")
@Validated
public class ProductoController {
	
	private final IProductoService productoService;

	public ProductoController(IProductoService productoService) {
		this.productoService = productoService;
	}
	
	@Operation(summary = "Busqueda.", description = "Retorna todos los productos sin filtros.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Operacion exitosa."),
					@ApiResponse(responseCode = "500", description = "Error interno del servidor.")})
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<ProductoResponseDTO>>> getAllProductos() {
    	log.info("INGRESO A ProductoController.getAllProductos().");
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponseDTO<>(true, 
										   MensajeResponseUtil.SUCCESS, 
										   CodeResponseUtil.SUCCESS, 
										   LocalDateTime.now(), 
										   this.productoService.findAll()));
    }
    
	@Operation(summary = "Busqueda por ID.", description = "Retorna un producto filtrando por ID.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Operacion exitosa."),
					@ApiResponse(responseCode = "400", description = "Error en la peticion."),
					@ApiResponse(responseCode = "404", description = "Entidad no encontrada."),
					@ApiResponse(responseCode = "500", description = "Error interno del servidor.")}
					)
	@Parameters({@Parameter(name = "id", description = "ID del Producto"),})
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ProductoResponseDTO>> getProductoById(@PathVariable @Positive Long id) throws Exception {
    	log.info("INGRESO A ProductoController.getProductoById(). ID: " + id);
    	return ResponseEntity.status(HttpStatus.OK)
					.body(new ApiResponseDTO<>(true, 
											   MensajeResponseUtil.SUCCESS, 
											   CodeResponseUtil.SUCCESS, 
											   LocalDateTime.now(), 
											   this.productoService.findById(id)));
    }
    
	
	@Operation(summary = "Creacion.", description = "Crea un Producto con los datos del resquest.")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Creacion exitosa."),
					@ApiResponse(responseCode = "400", description = "Request erroneo."),					
					@ApiResponse(responseCode = "500", description = "Error interno del servidor.")})
    @PostMapping
    public ResponseEntity<ApiResponseDTO<ProductoResponseDTO>> createProducto(@Valid  @RequestBody ProductoRequestDTO req) {
    	log.info("INGRESO A ProductoController.createProducto().");
   		return ResponseEntity.status(HttpStatus.CREATED)
					.body(new ApiResponseDTO<>(true, 
											   MensajeResponseUtil.CREATED, 
											   CodeResponseUtil.CREATED, 
											   LocalDateTime.now(), 
											   this.productoService.save(req)));
    }
    
	@Operation(summary = "Actualizacion.", description = "Actualiza un Producto con los datos del resquest.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Actualizacion exitosa."),
					@ApiResponse(responseCode = "400", description = "Request erroneo."),
					@ApiResponse(responseCode = "404", description = "Entidad no encontrada."),
					@ApiResponse(responseCode = "500", description = "Error interno del servidor.")})
	@Parameters({@Parameter(name = "id", description = "ID del Producto."),})
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ProductoResponseDTO>> updateProducto(@PathVariable @Positive Long id,   @RequestBody @Valid ProductoRequestDTO req) {
    	log.info("INGRESO A ProductoController.updateProducto().");
    	return ResponseEntity.status(HttpStatus.OK)
					.body(new ApiResponseDTO<>(true, 
											   MensajeResponseUtil.SUCCESS, 
											   CodeResponseUtil.SUCCESS, 
											   LocalDateTime.now(), 
											   this.productoService.update(id, req)));
        	
    }
    
	@Operation(summary = "Busqueda por ID.", description = "Elimina un producto filtrando por ID.")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Operacion exitosa."),	
					@ApiResponse(responseCode = "400", description = "Request erroneo."),
					@ApiResponse(responseCode = "404", description = "Entidad no encontrada."),
					@ApiResponse(responseCode = "500", description = "Error interno del servidor.")}
					)
	@Parameters({@Parameter(name = "id", description = "ID del Producto"),})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable @Positive Long id) {
    	log.info("INGRESO A ProductoController.deleteProducto(). ID: "+ id);
        productoService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}