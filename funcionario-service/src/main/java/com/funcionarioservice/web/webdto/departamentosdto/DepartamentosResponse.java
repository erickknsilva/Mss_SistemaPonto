package com.funcionarioservice.web.webdto.departamentosdto;

import com.funcionarioservice.domain.model.Departamentos;
import lombok.Builder;


@Builder
public record DepartamentosResponse(
        Integer id,

        String nome,

        String email,

        String telefone,

        String responsavel
) {

    public static DepartamentosResponse of(Departamentos departamentos) {
        return new DepartamentosResponse(departamentos.getId(), departamentos.getNome(), departamentos.getEmail(),
                departamentos.getTelefone(), departamentos.getResponsavel());
    }
}
