package com.notificacaoservice.rabbitmq;

import dto.PontoDispatcherMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;


@Configuration
public class DispatchingFunctions {

    private final EmailSend emailSend;

    public DispatchingFunctions(EmailSend emailSend) {
        this.emailSend = emailSend;
    }

    private static final Logger log = LoggerFactory.getLogger(DispatchingFunctions.class);

    @Bean
    public Consumer<Flux<PontoDispatcherMessage>> pack(EmailSend emailSend) {
        return flux -> emailSend.consumeMessageEvent(flux)
                .subscribe();
    }

    /**
     @Bean public Consumer<PontoDispatcherMessage> pack() {
     return pontoDispatcherMessage -> {
     log.info("Mensagem do funcionario com email: {} recebida com sucesso.", pontoDispatcherMessage.toString());
     };
     }

     @Bean public Function<PontoDispatcherMessage, String> pack() {
     return pontoDispatcherMessage -> {
     log.info("Mensagem do funcionario com email: {} recebida com sucesso.", pontoDispatcherMessage.emailfuncionario());
     return pontoDispatcherMessage.emailfuncionario();
     };
     }

     @RabbitListener(queues = "${spring.rabbitmq.queue.name}")
     public void onMessage(@Payload PontoDispatcherMessage pontoDispatcherMessage) {
     log.info("Mensagem recebida com sucesso matricula: {}, email: {}", pontoDispatcherMessage.funcionario(),
     pontoDispatcherMessage.emailfuncionario());
     System.out.println(pontoDispatcherMessage.toString());
     }
     **/
}
