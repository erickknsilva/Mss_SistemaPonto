package com.funcionarioservice.web.webdto.funcionariosdto;

import com.funcionarioservice.domain.model.Funcionarios;
import com.funcionarioservice.web.webdto.departamentosdto.DepartamentosResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

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

    public static FuncionariosResponse of(Funcionarios funcionarios) {
        return new FuncionariosResponse(funcionarios.getMatricula(), funcionarios.getNome(), funcionarios.getSobrenome(),
                funcionarios.getCargo(), funcionarios.getEmail(), funcionarios.getSalario(), funcionarios.getTipoContrato(),
                funcionarios.getDataEntrada(), DepartamentosResponse.of(funcionarios.getDepartamento()), funcionarios.getCargaDiaria(),
                funcionarios.getCargaSemanal(), funcionarios.getCargaMensal());
    }


}
