package com.funcionarioservice.web.webdto.departamentosdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record DepartamentoRequest(



        @NotBlank(message = "Insira o nome do departamento.")

        @Length(min = 4, max = 50, message = "O nome deve estar entre {min} e {max} caracteres. Experimente abreviar.")
        String nome,

        @Email
        @NotBlank(message = "Insira um email válido exemplo: teste@gmail.com")
        @Length(min = 4, max = 60, message = "O email deve estar entre {min} e {max} caracteres.")
        String email,

        @Length(max = 14, message = "O telefone deve conter entre {min} e {max} números.")
        String telefone,

        @Length(max = 80, message = "O nome deve estar entre {min} e {max} caracteres. Experimente abreviar.")
        String responsavel
) {
}
