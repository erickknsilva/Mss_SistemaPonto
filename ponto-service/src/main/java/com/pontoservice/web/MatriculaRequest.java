package com.pontoservice.web;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MatriculaRequest(
        @NotNull(message = "Insira a matricula do funcionario.")
        @Positive(message = "A matricula deve ser um numero positivo.")
        Integer matricula
) {
}
