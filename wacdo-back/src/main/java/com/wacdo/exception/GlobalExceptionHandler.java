package com.wacdo.exception;

import com.wacdo.dto.ApiError;
import jakarta.persistence.EntityNotFoundException;
import jdk.jshell.spi.ExecutionControl;
import org.apache.coyote.BadRequestException;
import org.hibernate.PropertyValueException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FunctionalException.class)
    public ResponseEntity<ApiError> handleFunctionalException(FunctionalException ex) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiError(HttpStatus.BAD_REQUEST.value(), "Erreur fonctionnelle", ex.getMessage()));
    }

    @ExceptionHandler(TechnicalException.class)
    public ResponseEntity<ApiError> handleTechnicalException(TechnicalException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erreur technique", ex.getMessage()));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiError> handleNoSuchElementException(NoSuchElementException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Aucun élément, entité retrouvé", ex.getMessage()));
    }

    @ExceptionHandler(InternalError.class)
    public ResponseEntity<ApiError> handleInternalError(InternalError ex) {
        return ResponseEntity
                .badRequest()
                .body(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erreur interne", ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ApiError(HttpStatus.BAD_REQUEST.value(), "Entité(s) non trouvée(s)", ex.getMessage()));
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ApiError> handleIntegrityConstraint(SQLIntegrityConstraintViolationException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ApiError(HttpStatus.BAD_REQUEST.value(), "Problème d'intégrité (sql)", ex.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ApiError(HttpStatus.BAD_REQUEST.value(), "Mauvaise requête", ex.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex) {

        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(PropertyValueException.class)
    public ResponseEntity<ApiError> handlePropertyValueException(PropertyValueException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erreur sur une propriété", ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handle(RuntimeException ex) {
        return ResponseEntity
                .badRequest()
                .body(Map.of("error", ex.getMessage()));
    }
}
