package it.unibas.wordle.modello;

import java.util.ArrayList;
import java.util.List;

public class Tentativo {

    private String parola;
    private List<Suggerimento> suggerimenti = new ArrayList<>();

    public Tentativo(String parola) {
        this.parola = parola;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public List<Suggerimento> getSuggerimenti() {
        return suggerimenti;
    }

    public void setSuggerimenti(List<Suggerimento> suggerimenti) {
        this.suggerimenti = suggerimenti;
    }

    public boolean isIndovinato(){
        if(suggerimenti.isEmpty()) {
            return false;
        }
        for(Suggerimento suggerimento : suggerimenti){
            if(!suggerimento.getSuggerimento().equals(Costanti.CORRETTA)){
                return false;
            }
        }
        return true;
    }
}
