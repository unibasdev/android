package it.unibas.wordle.modello;

import java.util.ArrayList;
import java.util.List;

public class Partita {

    private String parola;
    private List<Tentativo> tentativi = new ArrayList<>();

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public int getNumeroTentativi(){
        return tentativi.size();
    }

    public void aggiungiTentativo(Tentativo tentativo){
        if(tentativi.size() == Costanti.MAX_TENTATIVI){
            throw new IllegalArgumentException("La partita contiene gia' il massimo numero di tentativi");
        }
        this.tentativi.add(tentativo);
    }

    public Tentativo getTentativo(int indiceTentativo){
        if(indiceTentativo < 0 || indiceTentativo >= this.tentativi.size()){
            throw new IllegalArgumentException("Posizione tentativo scorretta");
        }
        return this.tentativi.get(indiceTentativo);
    }

    public boolean isConclusa(){
        if(tentativi.size() == Costanti.MAX_TENTATIVI){
            return true;
        }
        for(Tentativo tentativo : tentativi){
            if(tentativo.isIndovinato()){
                return true;
            }
        }
        return false;
    }
}
