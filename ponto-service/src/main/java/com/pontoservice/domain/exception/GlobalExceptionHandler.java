package com.pontoservice.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FuncionarioNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<String> handleFuncionarioCustomException(FuncionarioNotFoundException ex) {
        return Mono.just(ex.getReason());
    }

    @ExceptionHandler(PontoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<String> handlePontoCustomException(PontoNotFoundException ex) {
        return Mono.just(ex.getReason());
    }

    @ExceptionHandler(PontoNotRegistryAfterClose.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<String> handlePontoNotRegistryAfterClose(PontoNotRegistryAfterClose ex) {
        return Mono.just(ex.getReason());
    }
}