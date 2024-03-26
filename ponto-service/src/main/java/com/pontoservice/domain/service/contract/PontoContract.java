package com.pontoservice.domain.service.contract;

import com.pontoservice.domain.entity.Funcionario;
import com.pontoservice.domain.entity.Ponto;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalTime;

public interface PontoContract {

    BigDecimal calcularSalarioPorDia(BigDecimal salario, LocalTime horasTrabalhadas);

    Mono<Ponto> abrirPonto(Funcionario funcionario);

    Mono<Ponto> fecharPonto(Funcionario funcionario);
}
