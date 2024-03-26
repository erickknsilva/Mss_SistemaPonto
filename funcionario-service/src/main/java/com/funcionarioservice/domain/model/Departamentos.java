package com.funcionarioservice.domain.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@ToString
@Entity
@Table(name = "departamentos")
@EntityListeners(AuditingEntityListener.class)
public class Departamentos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Insira o nome do departamento.")
    @Length(min = 4, max = 50, message = "O nome deve estar entre {min} e {max} caracteres. Experimente abreviar.")
    private String nome;

    @Email
    @NotBlank(message = "Insira um email válido exemplo: teste@gmail.com")
    @Length(min = 4, max = 60, message = "O email deve estar entre {min} e {max} caracteres.")
    private String email;

    @Length(max = 14, message = "O telefone deve conter entre {min} e {max} números.")
    private String telefone;

    @Length(max = 80, message = "O nome deve estar entre {min} e {max} caracteres. Experimente abreviar.")
    private String responsavel;

    @CreatedDate
    private Instant createdDate;

    @LastModifiedDate
    private Instant lastModifiedDate;

    @Version
    int version;

    @JsonIgnore
    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Funcionarios> funcionario;


    public Departamentos(@NotBlank String nome, @NotBlank String email, String telefone, String responsavel) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.responsavel = responsavel;
    }

    public Departamentos(Integer id, String nome, String email, String telefone, String responsavel, Instant createdDate, Instant lastModifiedDate, int version) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.responsavel = responsavel;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.version = version;
    }

    public Departamentos(Integer id) {
        this.id = id;
    }

}
