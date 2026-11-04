package com.example.demo.model;

import java.util.Objects;
import java.util.UUID;

public record Parada(UUID id, int numero) {

    public Parada {
        Objects.requireNonNull(id, "O identificador da parada é obrigatório.");
        if (numero <= 0) {
            throw new IllegalArgumentException("O número da parada deve ser positivo.");
        }
    }

    public static Parada criar(int numero) {
        return new Parada(UUID.randomUUID(), numero);
    }
}
