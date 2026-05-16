package edu.javeriana.appweb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ManejadorGlobalExcepciones {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> manejarRuntimeException(
            RuntimeException ex){

        Map<String, Object> error = new HashMap<>();

        error.put("timestamp", LocalDateTime.now());
        error.put("mensaje", ex.getMessage());
        error.put("status", HttpStatus.BAD_REQUEST.value());

        return ResponseEntity
                .badRequest()
                .body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> manejarException(
            Exception ex){

        Map<String, Object> error = new HashMap<>();

        error.put("timestamp", LocalDateTime.now());
        error.put("mensaje", "Error interno del servidor");
        error.put("detalle", ex.getMessage());
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
    @ExceptionHandler(org.springframework.web.bind.support.WebExchangeBindException.class)
    public ResponseEntity<Map<String, Object>> manejarValidaciones(
            org.springframework.web.bind.support.WebExchangeBindException ex){

        Map<String, Object> error = new HashMap<>();

        Map<String, String> detalles = new HashMap<>();

        ex.getFieldErrors().forEach(fieldError ->
            detalles.put(
                    fieldError.getField(),
                    fieldError.getDefaultMessage()
            )
        );

        error.put("timestamp", LocalDateTime.now());
        error.put("status", HttpStatus.BAD_REQUEST.value());
        error.put("errores", detalles);

        return ResponseEntity
            .badRequest()
            .body(error);
    }
}