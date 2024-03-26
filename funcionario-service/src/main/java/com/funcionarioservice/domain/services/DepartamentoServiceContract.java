package com.funcionarioservice.domain.services;

import com.funcionarioservice.domain.services.contract.DepartamentoContract;
import com.funcionarioservice.domain.exceptions.departamento.DepartamentoAlreadyExistsException;
import com.funcionarioservice.domain.exceptions.departamento.DepartamentoNotFoundException;
import com.funcionarioservice.domain.model.Departamentos;
import com.funcionarioservice.domain.repository.DepartamentoRepository;
import com.funcionarioservice.web.webdto.departamentosdto.DepartamentosResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class DepartamentoServiceContract implements DepartamentoContract {

    private final DepartamentoRepository departamentoRepository;


    @Override
    public DepartamentosResponse save(Departamentos departamento) {

        if (departamentoRepository.existsByNome(departamento.getNome())) {
            throw new DepartamentoAlreadyExistsException(departamento.getNome());
        }

        return DepartamentosResponse.of(departamentoRepository.save(departamento));
    }

    @Override
    public DepartamentosResponse findById(Integer id) {

        return departamentoRepository.findById(id)
                .map(DepartamentosResponse::of)
                .orElseThrow(() -> new DepartamentoNotFoundException(id));
    }

    public DepartamentosResponse update(Integer id, Departamentos departamento) {

        return departamentoRepository.findById(id)
                .map(existingDep -> {
                    var depUpdate = new Departamentos(existingDep.getId(),
                            departamento.getNome(),
                            departamento.getEmail(),
                            departamento.getResponsavel(),
                            departamento.getTelefone(),
                            existingDep.getCreatedDate(),
                            existingDep.getLastModifiedDate(),
                            existingDep.getVersion());
                    return DepartamentosResponse.of(departamentoRepository.save(depUpdate));
                }).orElseGet(() -> save(departamento));
    }

    public void delete(Integer id) {

        Departamentos departamento = departamentoRepository.findById(id)
                .orElseThrow(() -> new DepartamentoNotFoundException(id));
        departamentoRepository.delete(departamento);

    }

    @Override
    public List<DepartamentosResponse> findAll() {

        return StreamSupport.stream(departamentoRepository.findAll().spliterator(), false)
                .map(DepartamentosResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    public Page<DepartamentosResponse> findAll(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "nome");
        Page<Departamentos> departamentosPage = departamentoRepository.findAll(pageable);

        List<DepartamentosResponse> listDepartamentoResponse = departamentosPage.getContent()
                .stream()
                .map(DepartamentosResponse::of)
                .collect(Collectors.toList());

        return new PageImpl<>(listDepartamentoResponse, pageable, departamentosPage.getTotalElements());
    }

}
