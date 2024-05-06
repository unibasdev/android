package it.unibas.mediapesataclient.modello;

import java.util.List;

public class Studente {
    private long id;
    private String nome;
    private String cognome;
    private int matricola;
    private int annoIscrizione;
    private Double mediaPesata;
    private List<Esame> listaEsami;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public int getMatricola() {
        return matricola;
    }

    public void setMatricola(int matricola) {
        this.matricola = matricola;
    }

    public int getAnnoIscrizione() {
        return annoIscrizione;
    }

    public void setAnnoIscrizione(int annoIscrizione) {
        this.annoIscrizione = annoIscrizione;
    }

    public Double getMediaPesata() {
        return mediaPesata;
    }

    public void setMediaPesata(Double mediaPesata) {
        this.mediaPesata = mediaPesata;
    }

    public List<Esame> getListaEsami() {
        return listaEsami;
    }

    public void setListaEsami(List<Esame> listaEsami) {
        this.listaEsami = listaEsami;
    }
}
