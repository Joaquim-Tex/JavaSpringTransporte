package com.example.demo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class ModelosTransporteTests {

    @Test
    void factoriesGenerateDistinctStableIdentifiers() {
        Linha linha = Linha.criar("Linha Centro");
        Onibus onibus = Onibus.criar(40, LocalTime.of(8, 30), EstadoOnibus.EM_TRANSITO);
        Parada parada = Parada.criar(1);

        assertNotEquals(Linha.criar("Linha Centro").id(), linha.id());
        assertNotEquals(Onibus.criar(40, LocalTime.of(8, 30), EstadoOnibus.EM_TRANSITO).id(), onibus.id());
        assertNotEquals(Parada.criar(1).id(), parada.id());
        assertEquals(linha.id(), new Linha(linha.id(), linha.nome()).id());
        assertEquals(onibus.id(), new Onibus(onibus.id(), onibus.capacidade(),
                onibus.horarioSaida(), onibus.estado()).id());
        assertEquals(parada.id(), new Parada(parada.id(), parada.numero()).id());
    }

    @Test
    void modelsRejectInvalidRequiredValues() {
        assertThrows(NullPointerException.class, () -> new Linha(null, "Linha Centro"));
        assertThrows(IllegalArgumentException.class, () -> Linha.criar(" "));
        assertThrows(NullPointerException.class, () -> new Onibus(
                UUID.randomUUID(), 40, null, EstadoOnibus.ESTACIONADO));
        assertThrows(IllegalArgumentException.class, () -> Onibus.criar(
                0, LocalTime.NOON, EstadoOnibus.ESTACIONADO));
        assertThrows(NullPointerException.class, () -> Onibus.criar(
                40, LocalTime.NOON, null));
        assertThrows(IllegalArgumentException.class, () -> Parada.criar(0));
    }
}
