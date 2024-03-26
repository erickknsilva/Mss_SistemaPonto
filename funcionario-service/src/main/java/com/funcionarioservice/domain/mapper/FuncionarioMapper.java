package com.funcionarioservice.domain.mapper;

import com.funcionarioservice.domain.model.Funcionarios;
import com.funcionarioservice.web.webdto.funcionariosdto.FuncionariosRequest;
import com.funcionarioservice.web.webdto.funcionariosdto.FuncionariosResponse;

public record FuncionarioMapper() {

    public static Funcionarios fromDtoToEntity(FuncionariosRequest request) {
        return new Funcionarios(request);
    }

//    public static Funcionarios fromResponseToEntity(FuncionariosResponse request) {
//        return new Funcionarios(request);
//    }
}
