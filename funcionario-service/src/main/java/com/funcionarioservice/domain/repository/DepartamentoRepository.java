package com.funcionarioservice.domain.repository;

import com.funcionarioservice.domain.model.Departamentos;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartamentoRepository
        extends PagingAndSortingRepository<Departamentos, Integer> {

    List<Departamentos> findAll();

    Departamentos save(Departamentos departamentos);

    Optional<Departamentos> findById(Integer id);


    boolean existsByNome(String nome);

    void delete(Departamentos departamentos);


}
