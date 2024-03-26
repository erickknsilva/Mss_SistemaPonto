package com.funcionarioservice.domain.services.contract;

import com.funcionarioservice.domain.model.Funcionarios;
import com.funcionarioservice.web.webdto.funcionariosdto.FuncionariosResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FuncionarioContract {

    List<FuncionariosResponse> findAll();

    Page<FuncionariosResponse> findAll(int page, int size);

    FuncionariosResponse findById(Integer matricula);

    FuncionariosResponse save(@Valid Funcionarios funcionario);

    FuncionariosResponse update(Integer matricula, @Valid Funcionarios funcionario);

    void delete(Integer matricula);


}
