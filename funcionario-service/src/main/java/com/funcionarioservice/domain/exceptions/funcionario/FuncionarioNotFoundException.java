package com.funcionarioservice.domain.exceptions.funcionario;

import org.springframework.http.HttpStatusCode;

public class FuncionarioNotFoundException extends RuntimeException {

    public FuncionarioNotFoundException(Integer id) {

        super("Funcionario com Id: " + id + ", não encontrado.");
    }


}
