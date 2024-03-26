package com.pontoservice.client;

import lombok.Builder;

@Builder
public record DepartamentosResponse(
        Integer id,

        String nome,

        String email,

        String telefone,

        String responsavel
) {


}
