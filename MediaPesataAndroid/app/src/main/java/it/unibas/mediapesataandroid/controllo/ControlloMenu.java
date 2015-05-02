package it.unibas.mediapesataandroid.controllo;

import android.net.Uri;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import it.unibas.mediapesataandroid.Applicazione;
import it.unibas.mediapesataandroid.Costanti;
import it.unibas.mediapesataandroid.R;
import it.unibas.mediapesataandroid.activity.ActivityPrincipale;
import it.unibas.mediapesataandroid.modello.Studente;

public class ControlloMenu {

    private final MenuItem.OnMenuItemClickListener azioneInformazioni = new AzioneInformazioni();
    private final MenuItem.OnMenuItemClickListener azioneModificaDatiStudente = new AzioneModificaDatiStudente();
    private final MenuItem.OnMenuItemClickListener azioneScegliFileImporta = new AzioneScegliFileImporta();
    private final MenuItem.OnMenuItemClickListener azioneScegliFileEsporta = new AzioneScegliFileEsporta();

    public MenuItem.OnMenuItemClickListener getAzioneModificaDatiStudente() {
        return azioneModificaDatiStudente;
    }

    public MenuItem.OnMenuItemClickListener getAzioneScegliFileImporta() {
        return azioneScegliFileImporta;
    }

    public MenuItem.OnMenuItemClickListener getAzioneScegliFileEsporta() {
        return azioneScegliFileEsporta;
    }

    public MenuItem.OnMenuItemClickListener getAzioneInformazioni() {
        return azioneInformazioni;
    }


    ///////////////////////////////////////////////////////////////////////////////////
    private class AzioneInformazioni implements MenuItem.OnMenuItemClickListener {

        public final String TAG = AzioneInformazioni.class.getSimpleName();

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
            activityPrincipale.mostraImpostazioni();
            return true;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////
    private class AzioneModificaDatiStudente implements MenuItem.OnMenuItemClickListener {

        public final String TAG = AzioneModificaDatiStudente.class.getSimpleName();

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
            activityPrincipale.schermoFormStudente();
            return true;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////
    private class AzioneScegliFileImporta implements MenuItem.OnMenuItemClickListener {

        public final String TAG = AzioneScegliFileImporta.class.getSimpleName();

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
            activityPrincipale.finestraApriFile();
            return true;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////
    private class AzioneScegliFileEsporta implements MenuItem.OnMenuItemClickListener {

        public final String TAG = AzioneScegliFileEsporta.class.getSimpleName();

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
            activityPrincipale.finestraSalvaFile();
            return true;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////

    public void azioneEsporta(Uri fileSelezionato) {
        Studente studente = (Studente) Applicazione.getInstance().getModello().getPersistentBean(Costanti.STUDENTE, Studente.class);
        OutputStream os = null;
        try {
            os = Applicazione.getInstance().getContentResolver().openOutputStream(fileSelezionato);
            Applicazione.getInstance().getDaoStudente().salva(studente, os);
        } catch (Exception e) {
            Log.e("Azione importa", "Impossibile importare lo studente", e);
            e.printStackTrace();
            String errore = Applicazione.getInstance().getString(R.string.StringaImpossibileSalvare) + ".\n" + e.getLocalizedMessage();
            Toast.makeText(Applicazione.getInstance(), errore, Toast.LENGTH_LONG).show();
//            ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
//            activityPrincipale.finestraErrore(errore);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////
    public void azioneImporta(Uri fileSelezionato) {
        InputStream is = null;
        try {
            is = Applicazione.getInstance().getContentResolver().openInputStream(fileSelezionato);
            Studente studente = (Studente) Applicazione.getInstance().getDaoStudente().carica(is, Studente.class);
            Applicazione.getInstance().getModello().saveBean(Costanti.STUDENTE, studente);
        } catch (Exception e) {
            Log.e("Azione importa", "Impossibile importare lo studente", e);
            e.printStackTrace();
            String errore = Applicazione.getInstance().getString(R.string.StringaImpossibileCaricare) + ".\n" + e.getLocalizedMessage();
            Toast.makeText(Applicazione.getInstance(), errore, Toast.LENGTH_LONG).show();
//            ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
//            activityPrincipale.finestraErrore(errore);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
