package com.projet.equipement.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    // ✅ Gestion des entités non trouvées -> 404
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        ApiError errorResponse = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                "Des erreurs de validation ont été détectées",
                request.getDescription(false),
                errors
        );

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }


    // 3️⃣ Violation de contrainte d’intégrité (unicité, clé étrangère, etc.)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        String message = resolveDataIntegrityMessage(ex);
        log.warn("Data integrity violation: {}", message);
        return buildResponse(HttpStatus.CONFLICT, "Conflit de données", message, request);
    }

    private ResponseEntity<ApiError> buildResponse(HttpStatus status, String error, String message, WebRequest request) {
        ApiError apiError = new ApiError(
                status.value(),
                error,
                message,
                request.getDescription(false)
        );
        return new ResponseEntity<>(apiError, status);
    }
    private String resolveDataIntegrityMessage(DataIntegrityViolationException ex) {
        Throwable cause = ex.getCause();

        if (cause instanceof org.hibernate.exception.ConstraintViolationException cve) {
            String constraintName = cve.getConstraintName();

            if (constraintName == null) return "Violation d'intégrité détectée";

            // 🎯 Règles génériques, quelle que soit l'entité
            if (constraintName.toUpperCase().contains("UNIQUE") || constraintName.toUpperCase().contains("UK_") || constraintName.toUpperCase().contains("UC_")) {
                return "Une ressource avec les mêmes données existe déjà (" + constraintName + ")";
            }
            if (constraintName.toUpperCase().contains("FK_")) {
                return "Référence non valide vers une autre entité (" + constraintName + ")";
            }
            if (constraintName.toUpperCase().contains("PK_")) {
                return "Clé primaire déjà utilisée (" + constraintName + ")";
            }
            return "Contrainte violée : " + constraintName;
        }

        return "Violation d'intégrité des données";
    }



    // ✅ Gestion des autres exceptions -> 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGlobalException(Exception ex, WebRequest request) {
        logger.error("Erreur détectée: ", ex);
        ApiError errorResponse = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<ApiError> handleInvalidOperation(InvalidOperationException ex, WebRequest request) {
        ApiError errorResponse = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Opération invalide",
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
