package com.funcionarioservice.domain.exceptions;

import com.funcionarioservice.domain.dto.ErrorResponseDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class BaseExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException exception) {
        var erros = new HashMap<String, String>();

        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            erros.put(fieldName, errorMessage);
        });

        return erros;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, List<String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, List<String>> errors = new HashMap<>();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String propertyPath = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();

            List<String> propertyErrors = errors.getOrDefault(propertyPath, new ArrayList<>());
            propertyErrors.add(errorMessage);
            errors.put(propertyPath, propertyErrors);
        }

        return errors;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponseDto dataIntegrityViolationException(DataIntegrityViolationException ex) {

        return ErrorResponseDto.builder()
                .message(ex.getCause().getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponseDto illegalArgumentException(IllegalArgumentException ex) {

        return ErrorResponseDto.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
    }
}
