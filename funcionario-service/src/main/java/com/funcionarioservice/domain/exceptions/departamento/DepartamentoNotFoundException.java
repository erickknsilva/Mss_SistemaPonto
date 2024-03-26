package com.funcionarioservice.domain.exceptions.departamento;

public class DepartamentoNotFoundException extends RuntimeException {

    public DepartamentoNotFoundException(Integer id) {

        super("Departamento com Id: " + id + ", não encontrado.");
    }


}
