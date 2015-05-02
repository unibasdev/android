package it.unibas.mediapesataandroid.controllo;

import android.view.MenuItem;

import it.unibas.mediapesataandroid.Applicazione;
import it.unibas.mediapesataandroid.Costanti;
import it.unibas.mediapesataandroid.R;
import it.unibas.mediapesataandroid.activity.ActivityFormStudente;
import it.unibas.mediapesataandroid.modello.ModelloPersistente;
import it.unibas.mediapesataandroid.modello.Studente;
import it.unibas.mediapesataandroid.vista.VistaFormStudente;

public class ControlloFormStudente {

    private final MenuItem.OnMenuItemClickListener azioneModificaStudente = new AzioneModificaStudente();
    private final MenuItem.OnMenuItemClickListener azioneAnnulla = new AzioneAnnulla();

    public MenuItem.OnMenuItemClickListener getAzioneModificaStudente() {
        return azioneModificaStudente;
    }

    public MenuItem.OnMenuItemClickListener getAzioneAnnulla() {
        return azioneAnnulla;
    }


    ///////////////////////////////////////////////////////////////////////////////////
    private class AzioneAnnulla implements MenuItem.OnMenuItemClickListener {

        public final static String TAG = "AzioneAnnulla";

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            ActivityFormStudente activityFormStudente = (ActivityFormStudente) Applicazione.getInstance().getCurrentActivity();
            activityFormStudente.finish();
            return true;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////
    private class AzioneModificaStudente implements MenuItem.OnMenuItemClickListener {

        public final static String TAG = "AzioneModificaStudente";

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            ActivityFormStudente activityFormStudente = (ActivityFormStudente) Applicazione.getInstance().getCurrentActivity();
            VistaFormStudente vistaFormStudente = activityFormStudente.getVistaFormStudente();
            boolean errori = convalidaStudente(vistaFormStudente);
            if (errori) {
                return true;
            }
            String nome = vistaFormStudente.getNome();
            String cognome = vistaFormStudente.getCognome();
            int matricola = Integer.parseInt(vistaFormStudente.getMatricola());
            ModelloPersistente modello = Applicazione.getInstance().getModello();
            Studente studente = (Studente) modello.getPersistentBean(Costanti.STUDENTE, Studente.class);
            studente.setNome(nome);
            studente.setCognome(cognome);
            studente.setMatricola(matricola);
            modello.saveBean(Costanti.STUDENTE, studente);
            activityFormStudente.finish();
            return true;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////
    private boolean convalidaStudente(VistaFormStudente vistaFormStudente) {
        Applicazione applicazione = Applicazione.getInstance();
        boolean errori = false;
        String stringaNome = vistaFormStudente.getNome();
        if (stringaNome == null || stringaNome.equals("")) {
            errori = true;
            vistaFormStudente.setErroreNome(applicazione.getString(R.string.StringaNomeVuoto));
        }
        String stringaCognome = vistaFormStudente.getCognome();
        if (stringaCognome == null || stringaCognome.equals("")) {
            errori = true;
            vistaFormStudente.setErroreCognome(applicazione.getString(R.string.StringaCognomeVuoto));
        }
        try {
            String stringaMatricola = vistaFormStudente.getMatricola();
            int matricola = Integer.parseInt(stringaMatricola);
            if (matricola < 0) {
                errori = true;
                vistaFormStudente.setErroreMatricola(applicazione.getString(R.string.StringaErroreMatricola));
            }
        } catch (NumberFormatException nfe) {
            vistaFormStudente.setErroreMatricola(applicazione.getString(R.string.StringaMatricolaNumero));
        }
        return errori;
    }
}
