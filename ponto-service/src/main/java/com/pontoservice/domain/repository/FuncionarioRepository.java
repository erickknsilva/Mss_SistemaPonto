package com.pontoservice.domain.repository;

import com.pontoservice.domain.entity.Funcionario;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface FuncionarioRepository extends ReactiveCrudRepository<Funcionario, Integer> {

    Funcionario findByMatricula(Integer matricula);

    Mono<Funcionario> findByEmail(String email);

    Mono<Boolean> existsByEmailOrMatricula(String email, Integer Matricula);

    Mono<Boolean> existsByMatricula(Integer matricula);


}
