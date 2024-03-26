package com.funcionarioservice.domain.model;

import com.funcionarioservice.web.webdto.funcionariosdto.FuncionariosRequest;
import com.funcionarioservice.web.webdto.funcionariosdto.FuncionariosResponse;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "funcionarios")
@EntityListeners(AuditingEntityListener.class)
public class Funcionarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer matricula;

    @Email
    @NotBlank(message = "Insira um email válido exemplo teste@gmail.com")
    @Length(min = 4, max = 80, message = "O email deve estar entre {min} e {max} caracteres.")
    private String email;


    @NotBlank(message = "Insira o primeiro nome do funcionário.")
    @Length(min = 4, max = 30, message = "O primeiro nome deve estar entre {min} e {max} caracteres.")
    private String nome;

    @NotNull(message = "O sobrenome não pode estar vazio.")
    @NotBlank(message = "Insira o sobrenome do funcionário.")
    @Length(min = 4, max = 60, message = "O sobrenome deve estar entre {min} e {max} caracteres.")
    private String sobrenome;

    @NotBlank(message = "Insira o cargo do funcionário.")
    @Length(min = 4, max = 70, message = "O sobrenome deve estar entre {min} e {max} caracteres.")
    private String cargo;


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

    private LocalDate dataEntrada;

    @NotNull(message = "Insira o departamento do funcionario.")
    @ManyToOne
    @JoinColumn(name = "departamento_id")
    private Departamentos departamento;

    @CreatedDate
    private Instant createdDate;

    @LastModifiedDate
    private Instant lastModifiedDate;

    @Version
    private int version;


    public Funcionarios(Integer matricula, String nome, String sobrenome, String cargo, String email, BigDecimal salario,
                        LocalTime cargaDiaria, Integer cargaSemanal, Integer cargaMensal, String tipoContrato,
                        LocalDate dataEntrada, Departamentos departamento, Instant createdDate, Instant lastModifiedDate, int version) {
        this.matricula = matricula;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cargo = cargo;
        this.email = email;
        this.salario = salario;
        this.cargaDiaria = cargaDiaria;
        this.cargaSemanal = cargaSemanal;
        this.cargaMensal = cargaMensal;
        this.tipoContrato = tipoContrato;
        this.dataEntrada = dataEntrada;
        this.departamento = departamento;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.version = version;
    }

    public Funcionarios(FuncionariosRequest request) {
        this.nome = request.nome();
        this.sobrenome = request.sobrenome();
        this.email = request.email();
        this.salario = request.salario();
        this.tipoContrato = request.tipoContrato();
        this.cargo = request.cargo();
        this.setDepartamento(request.departamento());
        this.cargaDiaria = request.cargaDiaria();
        this.cargaSemanal = request.cargaSemanal();
        this.cargaMensal = request.cargaMensal();
        this.dataEntrada = request.dataEntrada();

    }

    public Funcionarios(Integer matricula, String nome, String sobrenome,
                        String cargo, BigDecimal salario, LocalTime cargaDiaria, Integer cargaSemanal, Integer cargaMensal, String tipoContrato, LocalDate dataEntrada, Departamentos departamento, Instant createdDate, Instant lastModifiedDate, int version) {
        this.matricula = matricula;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cargo = cargo;
        this.salario = salario;
        this.cargaDiaria = cargaDiaria;
        this.cargaSemanal = cargaSemanal;
        this.cargaMensal = cargaMensal;
        this.tipoContrato = tipoContrato;
        this.dataEntrada = dataEntrada;
        this.departamento = departamento;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.version = version;
    }

    @Override
    public String toString() {
        return "Funcionario: " + "matricula: " + matricula
                + ", nome: " + nome.concat(" " + sobrenome)
                + ",\ndata entrada: " + dataEntrada + ", " + "departamento: "
                + departamento.getNome();
    }

}