package com.funcionarioservice.domain.mapper;

import com.funcionarioservice.domain.model.Departamentos;
import com.funcionarioservice.web.webdto.departamentosdto.DepartamentoRequest;
import com.funcionarioservice.web.webdto.departamentosdto.DepartamentosResponse;

public record DepartamentoMapper() {

    public static Departamentos fromDtoToEntity(DepartamentoRequest request) {
        return Departamentos.builder()
                .nome(request.nome())
                .email(request.email())
                .telefone(request.telefone())
                .responsavel(request.responsavel())
                .build();
    }


    public static Departamentos dtoResponseToEntity(DepartamentosResponse request) {
        return Departamentos.builder()
                .id(request.id())
                .nome(request.nome())
                .email(request.email())
                .telefone(request.telefone())
                .responsavel(request.responsavel())
                .build();
    }
}
