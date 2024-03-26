package com.pontoservice.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PontoNotRegistryAfterClose extends ResponseStatusException {

    public PontoNotRegistryAfterClose(HttpStatus status, String reason) {
        super(status, reason);
    }

    public PontoNotRegistryAfterClose(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }
}

