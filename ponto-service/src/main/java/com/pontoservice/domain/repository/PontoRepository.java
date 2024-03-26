package com.pontoservice.domain.repository;

import com.pontoservice.domain.entity.Funcionario;
import com.pontoservice.domain.entity.Ponto;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface PontoRepository extends ReactiveCrudRepository<Ponto, Integer> {


    Mono<Ponto> findFirstByFuncionarioOrderByDataDesc(Integer funcionario);

    Flux<Ponto> findByFuncionarioOrderByData(Funcionario funcionario);

    Mono<Ponto> findFirstByFuncionarioAndDataOrderByDataDesc(Integer funcionario, LocalDate data);


}
