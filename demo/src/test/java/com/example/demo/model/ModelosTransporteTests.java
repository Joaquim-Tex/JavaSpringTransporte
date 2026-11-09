package com.example.demo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class ModelosTransporteTests {

    @Test
    void factoriesGenerateDistinctStableIdentifiers() {
        Linha linha = Linha.criar("Linha Centro");
        Onibus onibus = Onibus.criar(linha.id(), 40, LocalTime.of(8, 30), EstadoOnibus.EM_TRANSITO);
        Parada parada = Parada.criar(linha.id(), 1, 1);

        assertNotEquals(Linha.criar("Linha Centro").id(), linha.id());
        assertNotEquals(Onibus.criar(linha.id(), 40, LocalTime.of(8, 30), EstadoOnibus.EM_TRANSITO).id(),
                onibus.id());
        assertNotEquals(Parada.criar(linha.id(), 1, 1).id(), parada.id());
        assertEquals(linha.id(), new Linha(linha.id(), linha.nome()).id());
        assertEquals(onibus.id(), new Onibus(onibus.id(), onibus.linhaId(), onibus.capacidade(),
                onibus.horarioSaida(), onibus.estado()).id());
        assertEquals(parada.id(), new Parada(parada.id(), parada.linhaId(),
                parada.numero(), parada.ordem()).id());
    }

    @Test
    void modelsRejectInvalidRequiredValues() {
        assertThrows(NullPointerException.class, () -> new Linha(null, "Linha Centro"));
        assertThrows(IllegalArgumentException.class, () -> Linha.criar(" "));
        assertThrows(NullPointerException.class, () -> new Onibus(
                UUID.randomUUID(), UUID.randomUUID(), 40, null, EstadoOnibus.ESTACIONADO));
        assertThrows(IllegalArgumentException.class, () -> Onibus.criar(
                UUID.randomUUID(), 0, LocalTime.NOON, EstadoOnibus.ESTACIONADO));
        assertThrows(NullPointerException.class, () -> Onibus.criar(
                UUID.randomUUID(), 40, LocalTime.NOON, null));
        assertThrows(IllegalArgumentException.class, () -> Parada.criar(UUID.randomUUID(), 0, 1));
        assertThrows(IllegalArgumentException.class, () -> Parada.criar(UUID.randomUUID(), 1, 0));
    }

    @Test
    void linhaOrdenaParadasEAsMantemImutaveis() {
        Linha linha = Linha.criar("Linha Centro");
        Parada segunda = Parada.criar(linha.id(), 20, 2);
        Parada primeira = Parada.criar(linha.id(), 10, 1);

        Linha linhaComParadas = linha.comParadas(List.of(segunda, primeira));

        assertEquals(List.of(primeira, segunda), linhaComParadas.paradas());
        assertThrows(UnsupportedOperationException.class, () -> linhaComParadas.paradas().clear());
    }

    @Test
    void linhaRejeitaParadasDeOutraLinhaEOrdensDuplicadas() {
        Linha linha = Linha.criar("Linha Centro");
        Parada parada = Parada.criar(linha.id(), 1, 1);

        assertThrows(IllegalArgumentException.class, () -> linha.comParadas(
                List.of(Parada.criar(UUID.randomUUID(), 2, 2))));
        assertThrows(IllegalArgumentException.class, () -> linha.comParadas(
                List.of(parada, Parada.criar(linha.id(), 2, 1))));
    }

    @Test
    void onibusRequerLinhaAssociada() {
        assertThrows(NullPointerException.class, () -> Onibus.criar(
                null, 40, LocalTime.NOON, EstadoOnibus.ESTACIONADO));
    }
}
