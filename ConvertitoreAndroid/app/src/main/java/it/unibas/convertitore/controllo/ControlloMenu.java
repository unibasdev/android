package it.unibas.convertitore.controllo;

import android.view.MenuItem;

import it.unibas.convertitore.Applicazione;
import it.unibas.convertitore.activity.ActivityPrincipale;

public class ControlloMenu {

    private final MenuItem.OnMenuItemClickListener azioneInformazioni = new AzioneInformazioni();

    public MenuItem.OnMenuItemClickListener getAzioneInformazioni() {
        return azioneInformazioni;
    }

    ///////////////////////////////////////////////////////////////////////////////////
    private class AzioneInformazioni implements MenuItem.OnMenuItemClickListener {

        public final static String TAG = "AzioneInformazioni";

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            ActivityPrincipale activity = (ActivityPrincipale)Applicazione.getInstance().getCurrentActivity();
            activity.mostraImpostazioni();
            return true;
        }
    }

}
