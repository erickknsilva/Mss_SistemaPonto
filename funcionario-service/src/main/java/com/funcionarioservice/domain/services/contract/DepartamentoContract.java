package com.funcionarioservice.domain.services.contract;

import com.funcionarioservice.domain.model.Departamentos;
import com.funcionarioservice.web.webdto.departamentosdto.DepartamentosResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DepartamentoContract {
    List<DepartamentosResponse> findAll();

    Page<DepartamentosResponse> findAll(int page, int size);

    DepartamentosResponse save(@Valid Departamentos departamento);

    DepartamentosResponse findById(Integer id);

    DepartamentosResponse update(Integer id, @Valid Departamentos departamentos);

    void delete(Integer id);

}
