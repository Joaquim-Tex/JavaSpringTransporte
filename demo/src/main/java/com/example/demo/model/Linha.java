package com.example.demo.model;

import java.util.Objects;
import java.util.UUID;

public record Linha(UUID id, String nome) {

    public Linha {
        Objects.requireNonNull(id, "O identificador da linha é obrigatório.");
        Objects.requireNonNull(nome, "O nome da linha é obrigatório.");
        if (nome.isBlank()) {
            throw new IllegalArgumentException("O nome da linha não pode estar vazio.");
        }
    }

    public static Linha criar(String nome) {
        return new Linha(UUID.randomUUID(), nome);
    }
}
