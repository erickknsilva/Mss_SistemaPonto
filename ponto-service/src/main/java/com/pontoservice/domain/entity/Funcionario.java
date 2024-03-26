package com.pontoservice.domain.entity;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("funcionario")
public class Funcionario {

    @Column("matricula")
    @Id
    private Integer matricula;

    @Email
    @NotBlank(message = "Insira um email valido exemplo teste@gmail.com")
    @Length(min = 4, max = 80, message = "O email deve estar entre {min} e {max} caractere.")
    private String email;

    @NotBlank(message = "Insira o sobrenome nome do funcionario.")
    @Length(min = 4, max = 60, message = "O sobrenome nome deve estar entre {min} e {max} caractere.")
    private String departamento;

    @Range(min = 5, message = "O valor mínimo para cadastrar é {min} reais")
    @Digits(integer = 6, fraction = 2, message = "Apenas centenas e 2 casas após o ponto.")
    @NotNull(message = "Insira o salário do funcionário")
    @Digits(integer = 6, fraction = 2, message = "Apenas centenas e 2 casas após o ponto. Exemplo 3234.54")
    private BigDecimal salario;

    @NotNull(message = "Insira a carga horária diária.")
    private LocalTime cargaDiaria;

    @Range(min = 0, message = "O valor mínimo para cadastrar a carga semanal é {min}")
    private Integer cargaSemanal;

    private Integer cargaMensal;

    @Length(min = 7, max = 10, message = "O valor deve estar entre {min} e {max} caracteres.")
    @NotBlank(message = "Insira o tipo de contrato do funcionário.")
    private String tipoContrato;

    @CreatedDate
    private Instant createdDate;

    @LastModifiedDate
    private Instant lastModifiedDate;

    @Version
    private int version;

}