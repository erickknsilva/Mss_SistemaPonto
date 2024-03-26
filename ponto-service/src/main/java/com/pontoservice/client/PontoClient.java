package com.pontoservice.client;

import com.pontoservice.config.ClientProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
public class PontoClient {

    private static final String FUNCIONARIO_ROUT_API = "/v1/funcionarios/";
    private final WebClient webClient;
    private final ClientProperties clientProperties;

    public PontoClient(WebClient webClient, ClientProperties clientProperties) {
        this.webClient = webClient;
        this.clientProperties = clientProperties;
    }

    public Mono<FuncionariosResponse> getFuncionarioByMatricula(Integer matricula) {
        return webClient.get()
                .uri(FUNCIONARIO_ROUT_API + matricula)
                .retrieve()
                .bodyToMono(FuncionariosResponse.class)
                .timeout(Duration.ofSeconds(clientProperties.tempLimit()), Mono.empty())
                .onErrorResume(WebClientResponseException.NotFound.class, (exception) -> Mono.empty())
                .retryWhen(Retry.backoff(3, Duration.ofMillis(100)))
                .onErrorResume(Exception.class, (exception) -> Mono.empty());
    }


}
