package com.funcionarioservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class FuncionarioServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FuncionarioServiceApplication.class, args);
    }

}
