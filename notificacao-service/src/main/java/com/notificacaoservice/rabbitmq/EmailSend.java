package com.notificacaoservice.rabbitmq;

import dto.PontoDispatcherMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmailSend {

    private final EmailQuery emailQuery;

    public EmailSend(EmailQuery emailQuery) {
        this.emailQuery = emailQuery;
    }

    private static final Logger log = LoggerFactory.getLogger(EmailSend.class);


    public Flux<Void> consumeMessageEvent(Flux<PontoDispatcherMessage> flux) {
        return flux
                .flatMap(pontoDispatcherMessage -> {
                    log.info("Processando mensagem de funcionário: {}", pontoDispatcherMessage.toString());

                    // Verifica a condição original com if
                    if (pontoDispatcherMessage.horaentrada() != null && pontoDispatcherMessage.horasaida() == null) {
                        return emailQuery.messageOpenPoint(pontoDispatcherMessage)
                                .then(Mono.fromRunnable(() -> emailQuery.messageClosePoint(pontoDispatcherMessage)));
                    } else {
                        return emailQuery.messageClosePoint(pontoDispatcherMessage).then();
                    }
                });
    }




}
