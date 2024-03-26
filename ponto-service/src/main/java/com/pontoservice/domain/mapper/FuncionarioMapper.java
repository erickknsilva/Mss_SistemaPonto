package com.pontoservice.domain.mapper;

import com.pontoservice.client.FuncionariosResponse;
import com.pontoservice.domain.entity.Funcionario;

public record FuncionarioMapper() {


    public static Funcionario of(FuncionariosResponse funcionario) {
        return Funcionario.builder()
                .matricula(funcionario.matricula())
                .email(funcionario.email())
                .cargaDiaria(funcionario.cargaDiaria())
                .cargaSemanal(funcionario.cargaSemanal())
                .cargaMensal(funcionario.cargaMensal())
                .salario(funcionario.salario())
                .tipoContrato(funcionario.tipoContrato())
                .departamento(funcionario.departamento().nome())
                .build();


    }
}
