package it.unibas.morracineseandroid.modello;

import it.unibas.morracineseandroid.Costanti;

public class Partita {
    private int maniVinteDalComputer = 0;
    private int maniVinteDalGiocatore = 0;
    private int pareggi = 0;
    private Mano ultimaMano;

    public int getManiVinteDalComputer() {
        return this.maniVinteDalComputer;
    }

    public int getManiVinteDalGiocatore() {
        return this.maniVinteDalGiocatore;
    }

    public int getPareggi() {
        return this.pareggi;
    }

    public Mano getUltimaMano() {
        return this.ultimaMano;
    }

    public Mano giocaMano(int giocata) {
        assert (this.getStato() == Costanti.PARTITAINCORSO) : "Partita conclusa. Stato partita: " + this.getStato();
        Mano mano = new Mano();
        mano.gioca(giocata);
        int esito = mano.getEsito();
        if (esito == Costanti.MANOVINTADALCOMPUTER) {
            this.maniVinteDalComputer++;
        } else if (esito == Costanti.MANOVINTADALGIOCATORE) {
            this.maniVinteDalGiocatore++;
        } else if (esito == Costanti.MANOINPAREGGIO) {
            this.pareggi++;
        }
        this.ultimaMano = mano;
        return mano;
    }

    public int getStato() {
        int stato = Costanti.PARTITAINCORSO;
        if (this.maniVinteDalComputer == Costanti.LIMITEMANI) {
            stato = Costanti.PARTITAVINTADALCOMPUTER;
        } else if (this.maniVinteDalGiocatore == Costanti.LIMITEMANI) {
            stato = Costanti.PARTITAVINTADALGIOCATORE;
        }
        return stato;
    }

}
