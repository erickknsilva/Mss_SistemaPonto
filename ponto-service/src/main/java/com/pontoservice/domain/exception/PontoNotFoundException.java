package com.pontoservice.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PontoNotFoundException extends ResponseStatusException {

    public PontoNotFoundException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public PontoNotFoundException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }
}

