package it.unibas.mediapesataandroid.controllo;

import android.view.MenuItem;

import java.util.Calendar;

import it.unibas.mediapesataandroid.Applicazione;
import it.unibas.mediapesataandroid.Costanti;
import it.unibas.mediapesataandroid.R;
import it.unibas.mediapesataandroid.activity.ActivityFormEsame;
import it.unibas.mediapesataandroid.modello.Esame;
import it.unibas.mediapesataandroid.modello.ModelloPersistente;
import it.unibas.mediapesataandroid.modello.Studente;
import it.unibas.mediapesataandroid.vista.VistaFormEsame;

public class ControlloFormEsame {

    private final MenuItem.OnMenuItemClickListener azioneAggiungiEsame = new AzioneAggiungiEsame();
    private final MenuItem.OnMenuItemClickListener azioneModificaEsame = new AzioneModificaEsame();
    private final MenuItem.OnMenuItemClickListener azioneAnnulla = new AzioneAnnulla();

    public MenuItem.OnMenuItemClickListener getAzioneAggiungiEsame() {
        return azioneAggiungiEsame;
    }

    public MenuItem.OnMenuItemClickListener getAzioneModificaEsame() {
        return azioneModificaEsame;
    }

    public MenuItem.OnMenuItemClickListener getAzioneAnnulla() {
        return azioneAnnulla;
    }


    ///////////////////////////////////////////////////////////////////////////////////
    private class AzioneAnnulla implements MenuItem.OnMenuItemClickListener {

        public final String TAG = AzioneAnnulla.class.getSimpleName();

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            ActivityFormEsame activityFormEsame = (ActivityFormEsame) Applicazione.getInstance().getCurrentActivity();
            activityFormEsame.finish();
            return true;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////
    private class AzioneAggiungiEsame implements MenuItem.OnMenuItemClickListener {

        public final String TAG = AzioneAggiungiEsame.class.getSimpleName();

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            ActivityFormEsame activityFormEsame = (ActivityFormEsame) Applicazione.getInstance().getCurrentActivity();
            VistaFormEsame vistaFormEsame = activityFormEsame.getVistaFormEsame();
            boolean errori = convalidaEsame(vistaFormEsame);
            if (errori) {
                return true;
            }
            String insegnamento = vistaFormEsame.getInsegnamento();
            int crediti = Integer.parseInt(vistaFormEsame.getCrediti());
            int voto = Integer.parseInt(vistaFormEsame.getVoto());
            boolean lode = vistaFormEsame.getLode();
            Calendar dataRegistrazione = vistaFormEsame.getDataRegistrazione();
            ModelloPersistente modello = Applicazione.getInstance().getModello();
            Studente studente = (Studente) modello.getPersistentBean(Costanti.STUDENTE, Studente.class);
            studente.addEsame(insegnamento, voto, lode, crediti, dataRegistrazione);
            modello.saveBean(Costanti.STUDENTE, studente);
            activityFormEsame.finish();
            return true;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////
    private class AzioneModificaEsame implements MenuItem.OnMenuItemClickListener {

        public final String TAG = AzioneModificaEsame.class.getSimpleName();

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            ActivityFormEsame activityFormEsame = (ActivityFormEsame) Applicazione.getInstance().getCurrentActivity();
            VistaFormEsame vistaFormEsame = activityFormEsame.getVistaFormEsame();
            boolean errori = convalidaEsame(vistaFormEsame);
            if (errori) {
                return true;
            }
            String insegnamento = vistaFormEsame.getInsegnamento();
            int crediti = Integer.parseInt(vistaFormEsame.getCrediti());
            int voto = Integer.parseInt(vistaFormEsame.getVoto());
            boolean lode = vistaFormEsame.getLode();
            Calendar dataRegistrazione = vistaFormEsame.getDataRegistrazione();
            ModelloPersistente modello = Applicazione.getInstance().getModello();
            Studente studente = (Studente) modello.getPersistentBean(Costanti.STUDENTE, Studente.class);
            Esame esameModificare = (Esame) modello.getPersistentBean(Costanti.ESAME, Esame.class);
            esameModificare.setInsegnamento(insegnamento);
            esameModificare.setVoto(voto);
            esameModificare.setLode(lode);
            esameModificare.setCrediti(crediti);
            esameModificare.setDataRegistrazione(dataRegistrazione);
            modello.saveBean(Costanti.STUDENTE, studente);
            activityFormEsame.finish();
            return true;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////
    private boolean convalidaEsame(VistaFormEsame vistaFormEsame) {
        Applicazione applicazione = Applicazione.getInstance();
        boolean errori = false;
        String stringaInsegnamento = vistaFormEsame.getInsegnamento();
        if (stringaInsegnamento == null || stringaInsegnamento.equals("")) {
            errori = true;
            vistaFormEsame.setErroreInsegnamento(applicazione.getString(R.string.StringaInsegnamentoVuoto));
        }
        String stringaCrediti = vistaFormEsame.getCrediti();
        if (stringaCrediti == null || stringaCrediti.equals("")) {
            errori = true;
            vistaFormEsame.setErroreCrediti(applicazione.getString(R.string.StringaErroreCrediti));
        } else {
            try {
                int crediti = Integer.parseInt(stringaCrediti);
                if (crediti <= 0 || crediti > 30) {
                    errori = true;
                    vistaFormEsame.setErroreCrediti(applicazione.getString(R.string.StringaErroreCrediti));
                }
            } catch (NumberFormatException nfe) {
                vistaFormEsame.setErroreCrediti(applicazione.getString(R.string.StringaCreditiNumero));
            }
        }
        String stringaVoto = vistaFormEsame.getVoto();
        if (stringaVoto == null || stringaVoto.equals("")) {
            errori = true;
            vistaFormEsame.setErroreVoto(applicazione.getString(R.string.StringaErroreVoto));
        } else {
            try {
                int voto = Integer.parseInt(stringaVoto);
                if (voto < 18 || voto > 30) {
                    errori = true;
                    vistaFormEsame.setErroreVoto(applicazione.getString(R.string.StringaErroreVoto));
                }
            } catch (NumberFormatException nfe) {
                vistaFormEsame.setErroreVoto(applicazione.getString(R.string.StringaVotoNumero));
            }
        }
        if (!errori) {
            boolean lode = vistaFormEsame.getLode();
            if (lode && Integer.parseInt(stringaVoto) != 30) {
                errori = true;
                vistaFormEsame.setErroreLode(applicazione.getString(R.string.StringaErroreLode));
            }
        }
        return errori;
    }
}
