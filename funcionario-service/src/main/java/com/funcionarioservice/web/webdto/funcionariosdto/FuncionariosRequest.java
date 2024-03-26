package com.funcionarioservice.web.webdto.funcionariosdto;

import com.funcionarioservice.domain.model.Departamentos;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record FuncionariosRequest(

        @NotBlank(message = "Insira o primeiro nome do funcionário.")
        @Length(min = 4, max = 30, message = "O primeiro nome deve estar entre {min} e {max} caracteres.")
        String nome,

        @NotNull(message = "O sobrenome não pode estar vazio.")
        @NotBlank(message = "Insira o sobrenome do funcionário.")
        @Length(min = 4, max = 60, message = "O sobrenome deve estar entre {min} e {max} caracteres.")
        String sobrenome,
        @NotBlank(message = "Insira o cargo do funcionário.")
        @Length(min = 4, max = 70, message = "O sobrenome deve estar entre {min} e {max} caracteres.")
        String cargo,
        @Email
        @NotBlank(message = "Insira um email válido exemplo teste@gmail.com")
        @Length(min = 4, max = 80, message = "O email deve estar entre {min} e {max} caracteres.")
        String email,

        @Range(min = 5, message = "O valor mínimo para cadastrar é {min} reais")
        @Digits(integer = 6, fraction = 2, message = "Apenas centenas e 2 casas após o ponto.")
        @NotNull(message = "Insira o salário do funcionário")
        @Digits(integer = 6, fraction = 2, message = "Apenas centenas e 2 casas após o ponto. Exemplo 3234.54")
        BigDecimal salario,

        @NotNull(message = "Insira a carga horária diária.")
        LocalTime cargaDiaria,

        @Range(min = 0, message = "O valor mínimo para cadastrar a carga semanal é {min}")
        Integer cargaSemanal,

        Integer cargaMensal,

        @Length(min = 7, max = 10, message = "O valor deve estar entre {min} e {max} caracteres.")
        @NotBlank(message = "Insira o tipo de contrato do funcionário.")
        String tipoContrato,

        LocalDate dataEntrada,

        @NotNull(message = "O departamento não pode ser nulo.")
        Departamentos departamento

) {
}
