package com.funcionarioservice.domain.exceptions.funcionario;

public class FuncionarioAlreadyExistsException extends RuntimeException {
    public FuncionarioAlreadyExistsException(String email) {
        super("Funcionario com email: " + email + ", já existe");
    }


}
