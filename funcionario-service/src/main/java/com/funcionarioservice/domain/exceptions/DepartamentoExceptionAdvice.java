package com.funcionarioservice.domain.exceptions;

import com.funcionarioservice.domain.dto.ErrorResponseDto;
import com.funcionarioservice.domain.exceptions.departamento.DepartamentoAlreadyExistsException;
import com.funcionarioservice.domain.exceptions.departamento.DepartamentoIdIllegalArgumentException;
import com.funcionarioservice.domain.exceptions.departamento.DepartamentoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DepartamentoExceptionAdvice extends BaseExceptionAdvice {


    @ExceptionHandler(DepartamentoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponseDto departamentoNotFoundException(DepartamentoNotFoundException exception) {
        return ErrorResponseDto.builder()
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
    }

    @ExceptionHandler(DepartamentoAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    ErrorResponseDto funcionarioAlreadyExistsException(DepartamentoAlreadyExistsException exception) {
        return ErrorResponseDto.builder()
                .message(exception.getMessage())
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .build();
    }

    @ExceptionHandler(DepartamentoIdIllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponseDto illegalArgumentException(DepartamentoIdIllegalArgumentException exception) {
        return ErrorResponseDto.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
    }


}
