package it.unibas.convertitore.controllo;

import android.view.MenuItem;

import it.unibas.convertitore.Applicazione;
import it.unibas.convertitore.activity.ActivityPrincipale;

public class ControlloMenu {

    private final MenuItem.OnMenuItemClickListener azioneImpostazioni = new ActivityImpostazioni();

    public MenuItem.OnMenuItemClickListener getAzioneImpostazioni() {
        return azioneImpostazioni;
    }

    ///////////////////////////////////////////////////////////////////////////////////
    private class ActivityImpostazioni implements MenuItem.OnMenuItemClickListener {

        public final String TAG = ActivityImpostazioni.class.getSimpleName();

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            ActivityPrincipale activity = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
            activity.mostraImpostazioni();
            return true;
        }
    }

}
