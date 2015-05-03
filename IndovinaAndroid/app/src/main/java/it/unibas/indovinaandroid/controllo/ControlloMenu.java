package it.unibas.indovinaandroid.controllo;

import android.util.Log;
import android.view.MenuItem;

import it.unibas.indovinaandroid.Applicazione;
import it.unibas.indovinaandroid.Costanti;
import it.unibas.indovinaandroid.activity.ActivityPartita;
import it.unibas.indovinaandroid.modello.Modello;
import it.unibas.indovinaandroid.modello.Partita;
import it.unibas.indovinaandroid.persistenza.DAOException;
import it.unibas.indovinaandroid.vista.VistaPartita;

public class ControlloMenu {

    private final MenuItem.OnMenuItemClickListener azioneVisualizzaRecord= new AzioneVisualizzaRecord();
    private final MenuItem.OnMenuItemClickListener azioneInformazioni = new AzioneInformazioni();
    private final MenuItem.OnMenuItemClickListener azioneNuovaPartita = new AzioneNuovaPartita();
    private final MenuItem.OnMenuItemClickListener azioneInterrompiPartita = new AzioneInterrompiPartita();

    public MenuItem.OnMenuItemClickListener getAzioneVisualizzaRecord() {
        return azioneVisualizzaRecord;
    }

    public MenuItem.OnMenuItemClickListener getAzioneInformazioni() {
        return azioneInformazioni;
    }

    public MenuItem.OnMenuItemClickListener getAzioneNuovaPartita() {
        return azioneNuovaPartita;
    }

    public MenuItem.OnMenuItemClickListener getAzioneInterrompiPartita() {
        return azioneInterrompiPartita;
    }


    ///////////////////////////////////////////////////////////////////////////////////
    public class AzioneNuovaPartita implements MenuItem.OnMenuItemClickListener {

        private static final String TAG = "AzioneNuovaPartita";

        @Override
        public boolean onMenuItemClick(MenuItem view) {
            Log.d(TAG, "Eseguo azione...");
            ActivityPartita activity = (ActivityPartita) Applicazione.getInstance().getCurrentActivity();
            Modello modello = Applicazione.getInstance().getModello();
            Partita partita = (Partita) modello.getBean(Costanti.PARTITA);
            partita.reset();
            VistaPartita vistaPartita = activity.getVistaPartita();
            vistaPartita.schermoNuovaPartita();
            return true;
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////
    public class AzioneInterrompiPartita implements MenuItem.OnMenuItemClickListener {

        private static final String TAG = "AzioneInterrompiPartita";

        @Override
        public boolean onMenuItemClick(MenuItem view) {
            Log.d(TAG, "Eseguo azione...");
            ActivityPartita activityPartita = (ActivityPartita) Applicazione.getInstance().getCurrentActivity();
            VistaPartita vistaPartita = activityPartita.getVistaPartita();
            vistaPartita.schermoPartitaInterrotta();
            return true;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////
    private class AzioneVisualizzaRecord implements MenuItem.OnMenuItemClickListener {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            ActivityPartita activityPartita = (ActivityPartita) Applicazione.getInstance().getCurrentActivity();
            int record;
            try {
                Applicazione applicazione = (Applicazione) activityPartita.getApplication();
                record = applicazione.getDaoRecord().carica();
            } catch (DAOException daoe) {
                record = 0;
            }
            activityPartita.finestraRecord(record);
            return true;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////
    private class AzioneInformazioni implements MenuItem.OnMenuItemClickListener {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            ActivityPartita activityPartita = (ActivityPartita) Applicazione.getInstance().getCurrentActivity();
            activityPartita.mostraImpostazioni();
            return true;
        }
    }
}
