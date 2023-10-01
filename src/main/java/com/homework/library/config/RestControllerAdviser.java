package com.homework.library.config;

import exception.ParamNotExistsException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestControllerAdviser {

    @ExceptionHandler(ParamNotExistsException.class)
    public ResponseEntity methodArgumentNotValidException(HttpServletRequest request, ParamNotExistsException e) {
        log.error("Error on processing of URL {}:", request.getRequestURI(), e);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}
