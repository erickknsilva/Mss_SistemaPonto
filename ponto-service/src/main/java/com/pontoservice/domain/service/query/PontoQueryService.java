package com.pontoservice.domain.service.query;

import com.pontoservice.client.FuncionariosResponse;
import com.pontoservice.domain.entity.Ponto;
import com.pontoservice.domain.exception.PontoNotRegistryAfterClose;
import com.pontoservice.domain.service.contract.PontoContractImplement;
import com.pontoservice.domain.entity.Funcionario;
import com.pontoservice.domain.mapper.FuncionarioMapper;
import com.pontoservice.domain.repository.FuncionarioRepository;
import com.pontoservice.domain.repository.PontoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PontoQueryService {

    private final FuncionarioRepository funcionarioRepository;
    private final PontoRepository pontoRepository;
    private final PontoContractImplement pontoContractImplement;

    public Mono<Funcionario> createOrGetFuncionario(FuncionariosResponse funcionario) {
        return funcionarioRepository.existsByMatricula(funcionario.matricula())
                .flatMap(exists -> {
                    if (exists) {
                        return funcionarioRepository.findById(funcionario.matricula());
                    } else {
                        return funcionarioRepository.save(FuncionarioMapper.of(funcionario));
                    }
                });
    }


    public Mono<Ponto> abrirOuFecharPonto(Funcionario funcionario) {
        return pontoRepository
                .findFirstByFuncionarioAndDataOrderByDataDesc(funcionario.getMatricula(), LocalDate.now())
                .flatMap(pontoAnterior -> {
                    if (pontoAnterior.getHorasaida() != null && pontoAnterior.getData().isEqual(LocalDate.now())) {
                        // Se não houver ponto anterior, abrir o ponto
                        throw new PontoNotRegistryAfterClose(HttpStatus.BAD_REQUEST,
                                "Ponto não pode ser registrado depois de fechado, no mesmo dia.");
                    } else {
                        // Se o ponto anterior estiver fechado, abrir um novo ponto
                        return pontoContractImplement.fecharPonto(funcionario);
                    }
                })
                .switchIfEmpty(pontoContractImplement.abrirPonto(funcionario));
    }


}
