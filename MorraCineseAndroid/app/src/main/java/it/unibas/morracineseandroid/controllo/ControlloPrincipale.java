package it.unibas.morracineseandroid.controllo;

import android.util.Log;
import android.view.View;

import it.unibas.morracineseandroid.Applicazione;
import it.unibas.morracineseandroid.Costanti;
import it.unibas.morracineseandroid.activity.ActivityPrincipale;
import it.unibas.morracineseandroid.modello.Gioco;
import it.unibas.morracineseandroid.modello.Mano;
import it.unibas.morracineseandroid.modello.Partita;
import it.unibas.morracineseandroid.vista.VistaPrincipale;

public class ControlloPrincipale {

    private final View.OnClickListener azioneGiocaCarta = new AzioneGioca(Costanti.CARTA);
    private final View.OnClickListener azioneGiocaForbici = new AzioneGioca(Costanti.FORBICI);
    private final View.OnClickListener azioneGiocaSasso = new AzioneGioca(Costanti.SASSO);

    public View.OnClickListener getAzioneGiocaCarta() {
        return azioneGiocaCarta;
    }

    public View.OnClickListener getAzioneGiocaForbici() {
        return azioneGiocaForbici;
    }

    public View.OnClickListener getAzioneGiocaSasso() {
        return azioneGiocaSasso;
    }


    private class AzioneGioca implements View.OnClickListener {

        private static final String TAG = "AzioneGioca";

        private int giocata;

        public AzioneGioca(int giocata) {
            this.giocata = giocata;
        }

        @Override
        public void onClick(View v) {
            ActivityPrincipale activity = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
            VistaPrincipale vistaPrincipale = activity.getVistaPrincipale();
            Partita partita = (Partita) Applicazione.getInstance().getModello().getBean(Costanti.PARTITA);
            Mano mano = partita.giocaMano(giocata);
            Applicazione.getInstance().getModello().putBean(Costanti.MANO, mano);
            vistaPrincipale.schermoPartita();
            if (partita.getStato() != Costanti.PARTITAINCORSO) {
                Gioco gioco = (Gioco) Applicazione.getInstance().getModello().getBean(Costanti.GIOCO);
                gioco.gestisciPartita(partita);
                vistaPrincipale.schermoFinePartita();
            }
            Log.d(TAG, "Ho giocato " + giocata);
        }
    }
}
