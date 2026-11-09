package com.example.demo.model;

import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

public record Onibus(
        UUID id,
        UUID linhaId,
        int capacidade,
        LocalTime horarioSaida,
        EstadoOnibus estado) {

    public Onibus {
        Objects.requireNonNull(id, "O identificador do ônibus é obrigatório.");
        Objects.requireNonNull(linhaId, "A linha do ônibus é obrigatória.");
        if (capacidade <= 0) {
            throw new IllegalArgumentException("A capacidade do ônibus deve ser positiva.");
        }
        Objects.requireNonNull(horarioSaida, "O horário de saída é obrigatório.");
        Objects.requireNonNull(estado, "O estado do ônibus é obrigatório.");
    }

    public static Onibus criar(UUID linhaId, int capacidade, LocalTime horarioSaida, EstadoOnibus estado) {
        return new Onibus(UUID.randomUUID(), linhaId, capacidade, horarioSaida, estado);
    }
}
