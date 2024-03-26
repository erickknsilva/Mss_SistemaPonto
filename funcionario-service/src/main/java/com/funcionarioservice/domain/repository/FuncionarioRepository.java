package com.funcionarioservice.domain.repository;

import com.funcionarioservice.domain.model.Funcionarios;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FuncionarioRepository
        extends PagingAndSortingRepository<Funcionarios, Integer> {

    Optional<Funcionarios> findByMatricula(Integer matricula);

    List<Funcionarios> findAll();

    Funcionarios save(Funcionarios funcionarios);

    boolean existsByEmail(String email);

    void delete(Funcionarios matricula);

}
