CREATE SCHEMA IF NOT EXISTS dat108_oblig5_h26;

CREATE TABLE dat108_oblig5_h26.deltager (
    mobil       CHARACTER(8) PRIMARY KEY,
    passordhash CHARACTER(64) NOT NULL,
    fornavn     CHARACTER VARYING(40),
    etternavn   CHARACTER VARYING(40),
    kjonn       CHARACTER VARYING(6)
);