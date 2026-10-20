package no.hvl.dat108.oblig4.service;

import no.hvl.dat108.oblig4.model.Deltager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeltagerDbService {

    private static List<Deltager> deltagerliste = new ArrayList<>(List.of(
        new Deltager("12345678", "dummyhash", "Knut", "Knutsen", "mann"),
        new Deltager("23456789", "dummyhash", "Knut", "Olsen", "mann"),
        new Deltager("34567890", "dummyhash", "Anne", "Annesen", "kvinne"),
        new Deltager("11223344", "dummyhash", "Lise", "Hansen", "kvinne")
    ));

    /**
     * @param mobil
     * @return En deltager med gitt mobilnummer, null hvis ingen.
     */
    public static Deltager finnDeltagerMedMobil(String mobil) {

        Deltager deltager = deltagerliste.stream().filter(d -> d.getMobil().equals(mobil)).findFirst().orElse(null);
        return deltager;
    }

    /**
     * @return Alle deltagerne, sortert stigende på fullt navn.
     */
    public static List<Deltager> finnAlleSortertPaaFulltNavn() {

        List<Deltager> list = deltagerliste.stream().sorted((a,b) -> a.getFulltNavn().compareTo(b.getFulltNavn())).toList();
        return list;
    }

    /**
     * @param mobil
     * @return Om deltager med gitt mobil er påmeldt
     */
    public static boolean erDeltagerPaameldt(String mobil) {
        return deltagerliste.stream().anyMatch(d -> d.getMobil().equals(mobil));
    }

    /**
     * Lagrer (melder på) en deltager ved å legge til i listen.
     * @param deltager
     */
    public static void lagreDeltager(Deltager deltager) {

        //TODO - Skriv denne metoden.
    }
}
