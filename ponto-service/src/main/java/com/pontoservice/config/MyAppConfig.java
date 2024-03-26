package com.pontoservice.config;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;

import java.util.List;

@Configuration
public class MyAppConfig extends AbstractR2dbcConfiguration {


    @Override
    public ConnectionFactory connectionFactory() {
        return ConnectionFactories.get("r2dbc:postgresql://localhost:5432/pontodb_ponto");
    }

    @Override
    protected List<Object> getCustomConverters() {
        return List.of(new PersonReadConverter(), new PersonWriteConverter());
    }
}