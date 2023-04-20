package it.unibas.wordle.modello;

import android.util.Log;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Operatore {

    private static final String TAG = Operatore.class.getSimpleName();

    public Tentativo valutaTentativo(String parolaTentata, Partita partita){
        parolaTentata = parolaTentata.toUpperCase();
        Tentativo tentativo = new Tentativo(parolaTentata);
        String parolaDaIndovinare = partita.getParola();
        if(parolaTentata.length() != parolaDaIndovinare.length()){
            throw new IllegalArgumentException("La parola tentata non ha la stessa lunghezza della parola da indovinare");
        }
        Set<Integer> posizioniLettereGestite = new HashSet<>();
        Map<Integer, String> mappaSuggerimenti = new HashMap<>();
        for(int i = 0; i < parolaTentata.length(); i++){
            if(parolaDaIndovinare.charAt(i) == parolaTentata.charAt(i)){
                posizioniLettereGestite.add(i);
                mappaSuggerimenti.put(i, Costanti.CORRETTA);
                Log.d(TAG, "Lettera " + parolaTentata.charAt(i) + " in posizione " + i + " corretta");
            }
        }
        for(int i = 0; i < parolaTentata.length(); i++){
            if(mappaSuggerimenti.containsKey(i)){ //Lettera gia' indovinata
                continue;
            }
            Integer indiceLetteraDisponibile = letteraDisponibile(parolaTentata.charAt(i), parolaDaIndovinare, posizioniLettereGestite);
            if(indiceLetteraDisponibile != null){
                Log.d(TAG, "Lettera " + parolaTentata.charAt(i) + " in posizione " + i + " e' presente, ma in posizione scorretta " + indiceLetteraDisponibile);
                posizioniLettereGestite.add(indiceLetteraDisponibile);
                mappaSuggerimenti.put(i, Costanti.POSIZIONE_SCORRETTA);
            }
        }
        for(int i = 0; i < parolaTentata.length(); i++){
            String suggerimento = mappaSuggerimenti.get(i);
            if(suggerimento == null){
                suggerimento = Costanti.NON_PRESENTE;
                Log.d(TAG, "Lettera " + parolaTentata.charAt(i) + " in posizione " + i + " non presente");
            }
            tentativo.getSuggerimenti().add(new Suggerimento(parolaTentata.charAt(i), suggerimento));
        }
        return tentativo;
    }

    private Integer letteraDisponibile(char charAt, String parolaDaIndovinare, Set<Integer> posizioniLettereGestite) {
        for(int i = 0; i < parolaDaIndovinare.length(); i++){
            if(posizioniLettereGestite.contains(i)){
                continue;
            }
            if(parolaDaIndovinare.charAt(i) == charAt){
                return i;
            }
        }
        return null;
    }

}
