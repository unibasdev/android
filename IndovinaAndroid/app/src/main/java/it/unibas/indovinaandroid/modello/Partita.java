package it.unibas.indovinaandroid.modello;

import android.util.Log;

import it.unibas.indovinaandroid.Applicazione;
import it.unibas.indovinaandroid.R;

public class Partita {

    private static java.util.Random generatore = new java.util.Random();

    private String nome;
    private int numeroDaIndovinare;
    private int numeroDiTentativi;
    private boolean trovato;
    private String suggerimento;

    public Partita() {
        reset();
    }

    public void reset() {
        this.numeroDaIndovinare = Math.abs(Partita.generatore.nextInt(100) + 1);
        Log.d("Partita", "Ho scelto il numero " + numeroDaIndovinare);
        this.trovato = false;
        this.numeroDiTentativi = 0;
        this.suggerimento = Applicazione.getInstance().getString(R.string.suggerimentoIniziale);
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void gestisciTentativo(int tentativo) {
        if ((tentativo < 1) || (tentativo > 100)) {
            throw new IllegalArgumentException("Il valore deve essere compreso tra 1 e 100");
        }
        this.numeroDiTentativi++;
        if (tentativo == this.numeroDaIndovinare) {
            this.trovato = true;
            this.suggerimento = Applicazione.getInstance().getString(R.string.suggerimentoNumeroIndovinato);
        } else if (tentativo < numeroDaIndovinare) {
            this.suggerimento = Applicazione.getInstance().getString(R.string.suggerimentoAlto, tentativo);
        } else if (tentativo > numeroDaIndovinare) {
            this.suggerimento = Applicazione.getInstance().getString(R.string.suggerimentoBasso, tentativo);
        }
    }

    public boolean getTrovato() {
        return this.trovato;
    }

    public String getSuggerimento() {
        return this.suggerimento;
    }

    public int getNumeroDiTentativi() {
        return this.numeroDiTentativi;
    }

    public int getNumeroDaIndovinare() {
        return this.numeroDaIndovinare;
    }

    public String getNome() {
        return this.nome;
    }
}
