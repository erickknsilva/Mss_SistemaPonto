package com.pontoservice.domain.service.contract;

import com.pontoservice.domain.entity.Funcionario;
import com.pontoservice.domain.entity.Ponto;
import com.pontoservice.domain.exception.PontoNotFoundException;
import com.pontoservice.domain.repository.FuncionarioRepository;
import com.pontoservice.domain.repository.PontoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

@Service
@RequiredArgsConstructor
public class PontoContractImplement implements PontoContract {

    private final PontoRepository pontoRepository;
    private final FuncionarioRepository funcRepository;


    @Override
    public BigDecimal calcularSalarioPorDia(BigDecimal salario, LocalTime horasTrabalhadas) {

        BigDecimal salarioPorHora = salario
                .setScale(2, RoundingMode.DOWN);

        BigDecimal minutosDecimal = new BigDecimal(horasTrabalhadas.getMinute())
                .divide(new BigDecimal(60), MathContext.DECIMAL32);

        BigDecimal horasTrabalhadasDecimal = new BigDecimal(horasTrabalhadas.getHour()).add(minutosDecimal);

        BigDecimal salarioPorDia = salarioPorHora.multiply(horasTrabalhadasDecimal);

        return salarioPorDia.setScale(2, RoundingMode.HALF_DOWN);

    }

    @Override
    public Mono<Ponto> abrirPonto(Funcionario funcionario) {

        LocalTime horaEntrada = LocalTime
                .of(LocalTime.now().getHour(), LocalTime.now().getMinute());

        return Mono.just(Ponto.builder()
                        .data(LocalDate.now())
                        .horaentrada(horaEntrada)
                        .diatrabalhado(true)
                        .funcionario(funcionario.getMatricula())
                        .emailfuncionario(funcionario.getEmail())
                        .salariomes(funcionario.getSalario())
                        .tipocontrato(funcionario.getTipoContrato())
                        .cargadiaria(funcionario.getCargaDiaria())
                        .build())
                .flatMap(pontoRepository::save);
    }


    @Override
    public Mono<Ponto> fecharPonto(Funcionario funcionario) {
        // Recupera o primeiro ponto registrado para o funcionário
        return pontoRepository.
                findFirstByFuncionarioOrderByDataDesc(funcionario.getMatricula())
                .switchIfEmpty(Mono.error(new PontoNotFoundException(HttpStatus.NOT_FOUND,
                        "Ponto não encontrado")))
                .flatMap(this::atualizarPonto)
                .flatMap(this::processarPontoFechadoReativo);
    }


    private Mono<Ponto> atualizarPonto(Ponto ponto) {

        LocalTime horaSaida = LocalTime
                .of(LocalTime.now().getHour(), LocalTime.now().getMinute());

        ponto.setHorasaida(horaSaida);
        ponto.setHorastrabalhada(calcularHoraTrabalhada(ponto.getHoraentrada(), horaSaida));

        // Retorna o Mono do ponto atualizado
        return pontoRepository.save(ponto);
    }


    private LocalTime calcularHoraTrabalhada(LocalTime horaEntrada, LocalTime horaSaida) {
        return horaSaida.minusHours(horaEntrada.getHour())
                .minusMinutes(horaEntrada.getMinute());
    }


    public Mono<Ponto> processarPontoFechadoReativo(Ponto pontoFechado) {
        return Mono.defer(() ->
                funcRepository.findById(pontoFechado.getFuncionario())
                        .flatMap(funcionario -> {
                            if (funcionario.getTipoContrato().equalsIgnoreCase("horista")) {

                                pontoFechado.setSalariodia(
                                        calcularSalarioPorDia(funcionario.getSalario(), pontoFechado.getHorastrabalhada()));

                                return calcularSalarioMesAcumulado(funcionario, pontoFechado)
                                        .flatMap(salarioMesAcumulado -> {
                                            BigDecimal novoSalarioMes = salarioMesAcumulado.add(pontoFechado.getSalariodia());
                                            pontoFechado.setSalariomes(novoSalarioMes);
                                            return pontoRepository.save(pontoFechado);
                                        });
                            } else {
                                pontoFechado.setHoraentrada(pontoFechado.getHoraentrada());
                                pontoFechado.setHorasaida(pontoFechado.getHorasaida());
                                return pontoRepository.save(pontoFechado);
                            }
                        }));
    }

    public Mono<BigDecimal> calcularSalarioMesAcumulado(Funcionario funcionario, Ponto pontoFechado) {
        // Obtém o mês do ponto fechado ou do último ponto
        Month mesPontoFechado = (pontoFechado != null) ? pontoFechado.getData().getMonth() : LocalDate.now().getMonth();

        // Recupera uma lista de pontos anteriores relacionados a um determinado funcionário, ordenados por data.
        return pontoRepository.findByFuncionarioOrderByData(funcionario)
                .filter(ponto -> pontoFechado == null || !ponto.getId().equals(pontoFechado.getId()))
                .filter(ponto -> ponto.getData().getMonth() == mesPontoFechado) // Filtra pontos apenas para o mesmo mês
                .map(ponto -> ponto.getSalariodia() != null ? ponto.getSalariodia() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}

