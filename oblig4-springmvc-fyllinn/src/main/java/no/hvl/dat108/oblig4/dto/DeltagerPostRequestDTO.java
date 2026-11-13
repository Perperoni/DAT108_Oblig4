package no.hvl.dat108.oblig4.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import no.hvl.dat108.oblig4.model.Deltager;
import no.hvl.dat108.oblig4.model.Feilmeldinger;

public record DeltagerPostRequestDTO(

        @NotNull(message = Feilmeldinger.UGYLDIG_MOBIL_MLD)
        @Pattern(
                regexp = "^[0-9]{8}$",
                message = Feilmeldinger.UGYLDIG_MOBIL_MLD)
        String mobil,

        @NotNull(message = Feilmeldinger.UGYLDIG_PASSORD_MLD)
        @Size(
                min = 4, max = 64,
                message = Feilmeldinger.UGYLDIG_PASSORD_MLD)
        String passord,

        @NotNull(message = Feilmeldinger.UGYLDIG_FORNAVN_MLD)
        @Pattern(
                regexp = "^[A-ZÆØÅ][a-zA-ZæøåÆØÅ -]{1,19}$",
                message = Feilmeldinger.UGYLDIG_FORNAVN_MLD)
        String fornavn,

        @NotNull(message = Feilmeldinger.UGYLDIG_ETTERNAVN_MLD)
        @Pattern(
                regexp = "^[A-ZÆØÅ][a-zA-ZæøåÆØÅ-]{1,19}$",
                message = Feilmeldinger.UGYLDIG_ETTERNAVN_MLD)
        String etternavn,

        @NotNull(message = Feilmeldinger.UGYLDIG_KJONN_MLD)
        @Pattern(
                regexp = "^(mann|kvinne)$",
                message = Feilmeldinger.UGYLDIG_KJONN_MLD)
        String kjonn) {

    public Deltager lagDeltager(String hashetPassord) {
        return new Deltager(
                mobil, hashetPassord, fornavn, etternavn, kjonn);
    }
}