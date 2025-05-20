package com.redis.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponseDTO<T> {

	@Schema(description = "Resultado de la operacion:true/false.")
	private boolean success;
	@Schema(description = "Mensaje de la respuesta.")
	private String message;
	@Schema(description = "Codigo de la respuesta.")
	private int code;
	@Schema(description = "Momento de la respuesta.")
	private LocalDateTime timestamp;
	@Schema(description = "Datos. Cuerpo de la respuesta.")
	private T data;
}
