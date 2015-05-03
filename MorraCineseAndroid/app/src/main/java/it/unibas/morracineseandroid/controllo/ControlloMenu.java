package it.unibas.morracineseandroid.controllo;

import android.util.Log;
import android.view.MenuItem;

import it.unibas.morracineseandroid.Applicazione;
import it.unibas.morracineseandroid.Costanti;
import it.unibas.morracineseandroid.activity.ActivityPrincipale;
import it.unibas.morracineseandroid.modello.Partita;
import it.unibas.morracineseandroid.vista.VistaPrincipale;

public class ControlloMenu {

    private final MenuItem.OnMenuItemClickListener azioneInformazioni = new AzioneInformazioni();
    private final MenuItem.OnMenuItemClickListener azioneIniziaPartita = new AzioneIniziaPartita();
    private final MenuItem.OnMenuItemClickListener azioneInterrompiPartita = new AzioneInterrompiPartita();

    public MenuItem.OnMenuItemClickListener getAzioneInformazioni() {
        return azioneInformazioni;
    }

    public MenuItem.OnMenuItemClickListener getAzioneIniziaPartita() {
        return azioneIniziaPartita;
    }

    public MenuItem.OnMenuItemClickListener getAzioneInterrompiPartita() {
        return azioneInterrompiPartita;
    }

    private class AzioneIniziaPartita implements MenuItem.OnMenuItemClickListener {

        private static final String TAG = "AzioneIniziaPartita";

        @Override
        public boolean onMenuItemClick(MenuItem v) {
            Partita partita = new Partita();
            Applicazione.getInstance().getModello().putBean(Costanti.PARTITA, partita);
            ActivityPrincipale activity = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
            VistaPrincipale vistaPrincipale = activity.getVistaPrincipale();
            vistaPrincipale.schermoInizialePartita();
            Log.d(TAG, "Partita avviata...");
            return true;
        }
    }

    private class AzioneInterrompiPartita implements MenuItem.OnMenuItemClickListener {

        private static final String TAG = "AzioneInterrompiPartita";

        @Override
        public boolean onMenuItemClick(MenuItem v) {
            ActivityPrincipale activity = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
            VistaPrincipale vistaPrincipale = activity.getVistaPrincipale();
            vistaPrincipale.schermoPartitaInterrotta();
            Log.d(TAG, "Partita interrotta...");
            return true;
        }
    }

    private class AzioneInformazioni implements MenuItem.OnMenuItemClickListener {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            ActivityPrincipale activity = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
            activity.mostraImpostazioni();
            return true;
        }
    }

}
