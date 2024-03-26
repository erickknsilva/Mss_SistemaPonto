package com.pontoservice.web;

import com.pontoservice.domain.entity.Ponto;
import com.pontoservice.domain.service.PontoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/ponto")
public class PontoWeb {

    private final PontoService pontoService;

    public PontoWeb(PontoService pontoService) {
        this.pontoService = pontoService;
    }

    @PostMapping("/{matricula}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Ponto> registryPoint(@PathVariable @Valid Integer matricula) {
        return pontoService.registrarPonto(matricula);
    }

}
