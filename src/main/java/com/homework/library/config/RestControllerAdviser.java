package com.homework.library.config;

import com.homework.library.exception.ParamNotExistsException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class RestControllerAdviser {

    @ExceptionHandler(ParamNotExistsException.class)
    public ResponseEntity methodArgumentNotValidException(HttpServletRequest request, ParamNotExistsException e) {
        log.error("Error on processing of URL {}:", request.getRequestURI(), e);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity SQLIntegrityConstraintViolationException(HttpServletRequest request, SQLIntegrityConstraintViolationException e) {
        log.error("Error on processing of URL {}:", request.getRequestURI(), e);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Значение параметра должно быть уникальным");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        log.error("Validation error", ex);
        return errors;
    }

}
