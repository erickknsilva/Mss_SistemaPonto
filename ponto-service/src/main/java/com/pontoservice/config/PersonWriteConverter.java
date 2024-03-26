package com.pontoservice.config;

import com.pontoservice.domain.entity.Funcionario;
import com.pontoservice.domain.entity.Ponto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.r2dbc.core.Parameter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@WritingConverter
public class PersonWriteConverter implements Converter<Ponto, OutboundRow> {


    public OutboundRow convert(Ponto source) {
        OutboundRow row = new OutboundRow();
        row.put("id", Parameter.fromOrEmpty(source.getId(), Long.class));
        row.put("horaEntrada", Parameter.fromOrEmpty(source.getHoraentrada(), LocalTime.class));
        row.put("horaSaida", Parameter.fromOrEmpty(source.getHorasaida(), LocalTime.class));
        row.put("horasTrabalhada", Parameter.fromOrEmpty(source.getHorastrabalhada(), LocalTime.class));
        row.put("diaTrabalhado", Parameter.fromOrEmpty(source.isDiatrabalhado(), Boolean.class));
        row.put("data", Parameter.fromOrEmpty(source.getData(), LocalDate.class));
        row.put("emailFuncionario", Parameter.fromOrEmpty(source.getEmailfuncionario(), String.class));
        row.put("salarioDia", Parameter.fromOrEmpty(source.getSalariodia(), BigDecimal.class));
        row.put("salarioMes", Parameter.fromOrEmpty(source.getSalariomes(), BigDecimal.class));
        row.put("funcionario", Parameter.fromOrEmpty(source.getFuncionario(), Funcionario.class));
        row.put("tipoContrato", Parameter.fromOrEmpty(source.getTipocontrato(), String.class));
        row.put("cargaDiaria", Parameter.fromOrEmpty(source.getCargadiaria(), LocalTime.class));
        return row;
    }

}
