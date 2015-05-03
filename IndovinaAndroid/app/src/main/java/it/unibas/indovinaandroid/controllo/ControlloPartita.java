package it.unibas.indovinaandroid.controllo;

import android.util.Log;
import android.view.View;

import it.unibas.indovinaandroid.Applicazione;
import it.unibas.indovinaandroid.Costanti;
import it.unibas.indovinaandroid.activity.ActivityPartita;
import it.unibas.indovinaandroid.modello.Modello;
import it.unibas.indovinaandroid.modello.Partita;
import it.unibas.indovinaandroid.modello.Record;
import it.unibas.indovinaandroid.persistenza.DAOException;
import it.unibas.indovinaandroid.vista.VistaPartita;

public class ControlloPartita {

    private final View.OnClickListener azioneTentativo = new AzioneTentativo();

    public View.OnClickListener getAzioneTentativo() {
        return azioneTentativo;
    }

    ///////////////////////////////////////////////////////////////////////////////////
    public class AzioneTentativo implements View.OnClickListener {

        private static final String TAG = "AzioneTentativo";

        @Override
        public void onClick(View view) {
            Log.d(TAG, "Eseguo azione...");
            Applicazione applicazione = Applicazione.getInstance();
            ActivityPartita activityPartita = (ActivityPartita) Applicazione.getInstance().getCurrentActivity();
            VistaPartita vistaPartita = activityPartita.getVistaPartita();
            String stringaTentativo = vistaPartita.getTestoTentativo();
            String errori = convalida(stringaTentativo);
            if (!errori.equals("")) {
                vistaPartita.setErroreTentativo(errori);
                return;
            }
            Modello modello = applicazione.getModello();
            Partita partita = (Partita) modello.getBean(Costanti.PARTITA);
            partita.gestisciTentativo(Integer.parseInt(stringaTentativo));
            if (partita.getTrovato()) {
                int tentativi = partita.getNumeroDiTentativi();
                verificaRecord(applicazione, tentativi);
                vistaPartita.schermoFinePartita();
            } else {
                vistaPartita.schermoAggiornaPartita();
            }
        }

        private String convalida(String stringaTentativo) {
            String errori = "";
            try {
                int tentativo = Integer.parseInt(stringaTentativo);
                if (tentativo < 1 || tentativo > 100) {
                    errori += "Il tentativo deve essere compreso tra 1 e 100";
                }
            } catch (NumberFormatException nfe) {
                errori += "Il tentativo deve essere un numero";
            }
            return errori;
        }

        private void verificaRecord(Applicazione applicazione, int tentativi) {
            Record record = (Record) applicazione.getModello().getBean(Costanti.RECORD);
            int recordAttuale;
            try {
                recordAttuale = applicazione.getDaoRecord().carica();
            } catch (DAOException daoe) {
                recordAttuale = 101;
            }
            record.checkRecord(tentativi, recordAttuale);
            if (record.isNuovoRecord()) {
                try {
                    applicazione.getDaoRecord().salva(tentativi);
                } catch (DAOException daoe) {
                    Log.w(TAG, "Impossibile salvare il record " + daoe);
                }
            }
        }

    }
}
