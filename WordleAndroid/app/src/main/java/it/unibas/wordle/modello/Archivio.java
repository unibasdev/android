package it.unibas.wordle.modello;

import java.util.Set;

public class Archivio {

    private int dimensione;
    private Set<String> archivio;

    public int getDimensione() {
        return dimensione;
    }

    public void setDimensione(int dimensione) {
        this.dimensione = dimensione;
    }

    public Set<String> getArchivio() {
        return archivio;
    }

    public void setArchivio(Set<String> archivio) {
        this.archivio = archivio;
    }


}
