package dto;


import jakarta.validation.constraints.Digits;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;


@Builder
public record PontoDispatcherMessage(

        LocalTime horaentrada,

        LocalTime horasaida,
        LocalTime horastrabalhada,


        LocalDate data,

        @Length(min = 4, max = 85,
                message = "O nome do responsavel deve estar entre {min} e {max} caracteres.\nExperimente abreaviar.")
        String emailfuncionario,

        @Range(min = 2, message = "O valor minimo para cadastrar é {min} reais")
        @Digits(integer = 8, fraction = 2, message = "Apenas centenas e 2 casas após o ponto.")
        BigDecimal salariodia,

        @Range(min = 2, message = "O valor minimo para cadastrar é {min} reais")
        @Digits(integer = 8, fraction = 2, message = "Apenas centenas e 2 casas após o ponto.")
        BigDecimal salariomes,

        Integer funcionario,

        String tipoContrato,
        LocalTime cargaDiaria
) {

}

