package com.pontoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class PontoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PontoServiceApplication.class, args);
    }

}
