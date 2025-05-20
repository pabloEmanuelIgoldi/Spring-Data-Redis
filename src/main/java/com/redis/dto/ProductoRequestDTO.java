package com.redis.dto;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class ProductoRequestDTO {
	
	@Schema(description = "Nombre del producto.")
	@NotBlank
	private String nombre;
	
	@Schema(description = "Fecha de ingreso del producto.")
	@NotNull
	private Date fecha;
	
	@Schema(description = "Precio del producto.")
	@Positive
	private BigDecimal precio;
}
