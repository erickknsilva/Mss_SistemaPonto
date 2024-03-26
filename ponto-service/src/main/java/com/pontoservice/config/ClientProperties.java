package com.pontoservice.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

@ConfigurationProperties(prefix = "ponto")
public record ClientProperties(

        @NotNull
        URI funcionarioServiceUri,
        Long tempLimit
) {


}
