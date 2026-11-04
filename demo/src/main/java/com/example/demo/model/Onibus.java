package com.example.demo.model;

import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

public record Onibus(
        UUID id,
        int capacidade,
        LocalTime horarioSaida,
        EstadoOnibus estado) {

    public Onibus {
        Objects.requireNonNull(id, "O identificador do ônibus é obrigatório.");
        if (capacidade <= 0) {
            throw new IllegalArgumentException("A capacidade do ônibus deve ser positiva.");
        }
        Objects.requireNonNull(horarioSaida, "O horário de saída é obrigatório.");
        Objects.requireNonNull(estado, "O estado do ônibus é obrigatório.");
    }

    public static Onibus criar(int capacidade, LocalTime horarioSaida, EstadoOnibus estado) {
        return new Onibus(UUID.randomUUID(), capacidade, horarioSaida, estado);
    }
}
