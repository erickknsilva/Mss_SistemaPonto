package com.pontoservice.domain.service;

import com.pontoservice.client.PontoClient;
import com.pontoservice.domain.entity.Ponto;
import com.pontoservice.domain.exception.FuncionarioNotFoundException;
import com.pontoservice.domain.repository.FuncionarioRepository;
import com.pontoservice.domain.repository.PontoRepository;
import com.pontoservice.domain.service.contract.PontoContractImplement;
import com.pontoservice.domain.service.query.PontoQueryService;
import com.pontoservice.event.PontoDispatcherMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PontoService {

    private final PontoClient pontoClient;
    private final PontoQueryService pontoQueryService;
    private final StreamBridge streamBridge;

    private static final Logger log = LoggerFactory.getLogger(PontoService.class);

    @Transactional
    public Mono<Ponto> registrarPonto(Integer matricula) {
        return pontoClient.getFuncionarioByMatricula(matricula)
                .switchIfEmpty(Mono.error(new
                        FuncionarioNotFoundException(HttpStatus.NOT_FOUND, "Funcionario não encontrado")))
                .flatMap(pontoQueryService::createOrGetFuncionario)
                .flatMap(pontoQueryService::abrirOuFecharPonto)
                .doOnSuccess(this::publishOrderAcceptedEvent);
    }

    private void publishOrderAcceptedEvent(Ponto ponto) {

        if (ponto == null) {
            return;
        }
        var pontoDispatcherMessage = PontoDispatcherMessage.of(ponto);

        log.info("Encaminhando informações do ponto para o serviço de notificação\n" +
                "funcionario com email: {}", pontoDispatcherMessage.emailfuncionario());

        var result = streamBridge.send("pontoDispatch-out-0", pontoDispatcherMessage);

        log.info("Result of sending data for funcionario with matricula {}: {}",
                pontoDispatcherMessage.funcionario(), result);

        System.out.println(pontoDispatcherMessage.toString());
    }


}


