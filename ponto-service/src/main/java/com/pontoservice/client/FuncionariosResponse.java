package com.pontoservice.client;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record FuncionariosResponse(

        Integer matricula,
        String nome,

        String sobrenome,
        String cargo,

        String email,

        BigDecimal salario,
        String tipoContrato,
        LocalDate dataEntrada,
        DepartamentosResponse departamento,

        LocalTime cargaDiaria,

        Integer cargaSemanal,

        Integer cargaMensal


) {


}
