package com.funcionarioservice.domain.exceptions;

import com.funcionarioservice.domain.dto.ErrorResponseDto;
import com.funcionarioservice.domain.exceptions.funcionario.FuncionarioAlreadyExistsException;
import com.funcionarioservice.domain.exceptions.funcionario.FuncionarioNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FuncionarioExceptionAdvice extends BaseExceptionAdvice {


    @ExceptionHandler(FuncionarioNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto funcionarioNotFoundException(FuncionarioNotFoundException exception) {
        return ErrorResponseDto.builder()
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
    }

    @ExceptionHandler(FuncionarioAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    ErrorResponseDto funcionarioAlreadyExistsException(FuncionarioAlreadyExistsException exception) {
        return ErrorResponseDto.builder()
                .message(exception.getMessage())
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .build();
    }


}
