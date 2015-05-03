package it.unibas.convertitore.modello;

public class Convertitore {

    public double convertiInVotoLaurea(double voto30mi, int maxVotoLaurea) {
        return voto30mi / 30 * maxVotoLaurea;
    }

    public double convertiIn30mi(double votoLaurea, int maxVotoLaurea) {
        return votoLaurea / maxVotoLaurea * 30;
    }

}
