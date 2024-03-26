package com.pontoservice.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FuncionarioNotFoundException extends ResponseStatusException {

    public FuncionarioNotFoundException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public FuncionarioNotFoundException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }
}

