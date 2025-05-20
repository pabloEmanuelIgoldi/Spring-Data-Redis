package com.redis.util;

public class CodeResponseUtil {

	// Peticion exitosa
	public static final int SUCCESS = 1100;
	public static final int CREATED = 1101;

	// Errores de cliente	
	public static final int BAD_REQUEST = 1400;
	public static final int ENTITY_NOT_FOUND = 1404;
	
	// Errores del servidor
    public static final int INTERNAL_ERROR = 1500;
}
