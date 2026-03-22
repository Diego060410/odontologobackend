package com.citasodontologicas.backend.util;

public class AppConstants {

    private AppConstants() {
    }

    public static final String AUTH_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "Bearer";

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_ODONTOLOGO = "ODONTOLOGO";
    public static final String ROLE_PACIENTE = "PACIENTE";

    public static final String MSG_REGISTRO_EXITOSO = "Usuario registrado correctamente";
    public static final String MSG_LOGIN_EXITOSO = "Inicio de sesión exitoso";
    public static final String MSG_RECURSO_NO_ENCONTRADO = "Recurso no encontrado";
    public static final String MSG_ERROR_INTERNO = "Error interno del servidor";
}