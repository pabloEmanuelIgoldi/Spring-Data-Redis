package com.redis.dto;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoResponseDTO{
	
	@Schema(description = "ID del producto.")
	private Long id;
	@Schema(description = "Nombre del producto.")
	private String nombre;
	@Schema(description = "Fecha de ingreso del producto.")
	private Date fecha;
	@Schema(description = "Precio del producto.")
	private BigDecimal precio;
}
