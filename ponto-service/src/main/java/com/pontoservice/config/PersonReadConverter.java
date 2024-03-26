package com.pontoservice.config;

import com.pontoservice.domain.entity.Funcionario;
import com.pontoservice.domain.entity.Ponto;
import io.r2dbc.spi.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@ReadingConverter
public class PersonReadConverter implements Converter<Row, Ponto> {


    @Override
    public Ponto convert(Row source) {

        Ponto ponto = Ponto.builder()
                .id(source.get("id", Long.class))
                .horaentrada(source.get("horaEntrada", LocalTime.class))
                .horasaida(source.get("horaSaida", LocalTime.class))
                .horastrabalhada(source.get("horasTrabalhada", LocalTime.class))
                .diatrabalhado(Boolean.TRUE.equals(source.get("diaTrabalhado", Boolean.class)))
                .data(source.get("data", LocalDate.class))
                .emailfuncionario(source.get("emailFuncionario", String.class))
                .salariodia(source.get("salarioDia", BigDecimal.class))
                .salariomes(source.get("salarioMes", BigDecimal.class))
                .funcionario(source.get("funcionario", Integer.class))
                .tipocontrato(source.get("tipoContrato", String.class))
                .cargadiaria(source.get("cargaDiaria", LocalTime.class))
                .build();

        return ponto;
    }

}
