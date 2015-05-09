package it.unibas.mediapesataandroid.modello;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Studente {

    private String nome;
    private String cognome;
    private int matricola;
    private List<Esame> listaEsami = new ArrayList<Esame>();

    public Studente() {
        this.nome = "";
        this.cognome = "";
        this.matricola = 0;
    }

    public Studente(String nome, String cognome, int matricola) {
        this.nome = nome;
        this.cognome = cognome;
        this.matricola = matricola;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return this.cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public int getMatricola() {
        return this.matricola;
    }

    public void setMatricola(int matricola) {
        this.matricola = matricola;
    }

    public void addEsame(String insegnamento, int voto, boolean lode, int crediti, Calendar dataRegistrazione) {
        Esame esame = new Esame(insegnamento, voto, lode, crediti, dataRegistrazione);
        this.listaEsami.add(esame);
    }

    public void addEsame(Esame esame) {
        this.listaEsami.add(esame);
    }

    public Esame getEsame(int i) {
        if (i < 0 || i >= this.listaEsami.size()) {
            throw new IndexOutOfBoundsException("Esame inesistente");
        }
        return this.listaEsami.get(i);
    }

    public void eliminaEsame(int i) {
        if (i < 0 || i >= this.listaEsami.size()) {
            throw new IndexOutOfBoundsException("Esame inesistente");
        }
        this.listaEsami.remove(i);
    }

    public int getNumeroEsami() {
        return this.listaEsami.size();
    }

    public java.util.List getListaEsami() {
        return this.listaEsami;
    }

    public int getCreditiTotali() {
        int sommaCrediti = 0;
        for (Esame esame : this.listaEsami) {
            sommaCrediti += esame.getCrediti();
        }
        return sommaCrediti;
    }

    public double getMedia30mi() {
        if (this.listaEsami.isEmpty()) {
            throw new IllegalArgumentException("Non e' possibile calcolare la media di 0 esami");
        }
        int sommaVotiPesati = 0;
        int sommaCrediti = 0;
        for (Esame esame : this.listaEsami) {
            sommaVotiPesati += esame.getVoto() * esame.getCrediti();
            sommaCrediti += esame.getCrediti();
        }
        return ((double) sommaVotiPesati) / sommaCrediti;
    }

    public double getMedia110mi() {
        return getMedia30mi() / 30 * 110;
    }

    @Override
    public String toString() {
        return "Cognome: " + getCognome() + " - Nome: " + getNome()
                + " - Matricola: " + getMatricola() + " - " + listaEsami.size() + " esami";
    }

    public String toSaveString() {
        return getCognome() + " , " + getNome() + " , " + getMatricola();
    }

}


