package com.pontoservice.domain.entity;

import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("pontos")
public class Ponto {

    @Id
    private Long id;

    private LocalTime horaentrada;

    private LocalTime horasaida;

    private LocalTime horastrabalhada;

    @Builder.Default
    private boolean diatrabalhado = false;

    private LocalDate data;

    @Length(min = 4, max = 85, message = "O nome do responsavel deve estar entre {min} e {max} caracteres.\nExperimente abreaviar.")
    private String emailfuncionario;

    @Range(min = 2, message = "O valor minimo para cadastrar é {min} reais")
    @Digits(integer = 8, fraction = 2, message = "Apenas centenas e 2 casas após o ponto.")
    private BigDecimal salariodia;

    @Range(min = 2, message = "O valor minimo para cadastrar é {min} reais")
    @Digits(integer = 8, fraction = 2, message = "Apenas centenas e 2 casas após o ponto.")
    private BigDecimal salariomes;

    @Column("funcionario")
    private Integer funcionario;

    private String tipocontrato;

    private LocalTime cargadiaria;


    public Ponto(LocalTime horaEntrada, LocalTime horaSaida,
                 boolean diaTrabalhado, String nomeFuncionario)//
    {
        this.horaentrada = horaEntrada;
        this.horasaida = horaSaida;
        this.diatrabalhado = diaTrabalhado;
        this.data = LocalDate.now();
        this.emailfuncionario = nomeFuncionario;
    }

    public Ponto withFuncionario(Funcionario funcionario) {
        return Ponto.builder()
                .id(this.id)
                .horaentrada(this.horaentrada)
                .horasaida(this.horasaida)
                .horastrabalhada(this.horastrabalhada)
                .diatrabalhado(this.diatrabalhado)
                .data(this.data)
                .emailfuncionario(this.emailfuncionario)
                .salariodia(this.salariodia)
                .funcionario(funcionario.getMatricula())
                .build();
    }


}