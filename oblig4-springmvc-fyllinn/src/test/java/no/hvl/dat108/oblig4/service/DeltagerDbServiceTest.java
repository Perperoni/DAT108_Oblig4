package no.hvl.dat108.oblig4.service;

import no.hvl.dat108.oblig4.model.Deltager;
import no.hvl.dat108.oblig4.repository.DeltagerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DeltagerDbServiceTest {

    private DeltagerRepository repository;
    private DeltagerDbService service;

    @BeforeEach
    void setUp() {
        repository = mock(DeltagerRepository.class);
        service = new DeltagerDbService(repository);
    }

    @Test
    void finnAlleSortertPaaFulltNavnReturnererAlleSortert() {
        Deltager knutOlsen = new Deltager("12345678", "hash", "Knut", "Olsen", "mann");
        Deltager anne = new Deltager("23456789", "hash", "Anne", "Annesen", "kvinne");
        Deltager knutKnutsen = new Deltager("34567890", "hash", "Knut", "Knutsen", "mann");
        // Samme fulle navn men forskjellig mobil
        Deltager annenAnne = new Deltager("45678901", "hash", "Anne", "Annesen", "kvinne");
        when(repository.findAll()).thenReturn(List.of(knutOlsen, anne, knutKnutsen, annenAnne));

        assertEquals(List.of(anne, annenAnne, knutKnutsen, knutOlsen),
                service.finnAlleSortertPaaFulltNavn());
    }

    @Test
    void finnAlleSortertPaaFulltNavnHaandtererTomListe() {
        when(repository.findAll()).thenReturn(List.of());

        assertEquals(List.of(), service.finnAlleSortertPaaFulltNavn());
    }
}
