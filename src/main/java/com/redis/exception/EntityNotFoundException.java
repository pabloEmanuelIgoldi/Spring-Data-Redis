package com.redis.exception;

public class EntityNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private String mensaje;
	
	public EntityNotFoundException(String mensaje) {
		super();
		this.mensaje = mensaje;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}