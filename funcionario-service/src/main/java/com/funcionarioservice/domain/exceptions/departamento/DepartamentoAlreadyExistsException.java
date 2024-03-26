package com.funcionarioservice.domain.exceptions.departamento;

public class DepartamentoAlreadyExistsException extends RuntimeException {
    public DepartamentoAlreadyExistsException(String nome) {
        super("Departamento com o nome: " + nome + ", já existe");
    }


}
