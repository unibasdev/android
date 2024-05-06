package it.unibas.mediapesataclient.controllo;

import android.util.Log;
import android.view.View;

import java.time.LocalDate;
import java.time.LocalDateTime;

import it.unibas.mediapesataclient.Applicazione;
import it.unibas.mediapesataclient.activity.ActivityFormEsame;
import it.unibas.mediapesataclient.modello.Costanti;
import it.unibas.mediapesataclient.modello.Esame;
import it.unibas.mediapesataclient.modello.Studente;
import it.unibas.mediapesataclient.vista.VistaFormEsame;

public class ControlloFormEsame {
    private String TAG = ControlloFormEsame.class.getSimpleName();
    private View.OnClickListener azioneAggiungiEsame = new AzioneAggiungiEsame();

    public View.OnClickListener getAzioneAggiungiEsame() {
        return azioneAggiungiEsame;
    }

    private class AzioneAggiungiEsame implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            ActivityFormEsame activityFormEsame = (ActivityFormEsame) Applicazione.getInstance().getCurrentActivity();
            VistaFormEsame vistaFormEsame = activityFormEsame.getVistaFormEsame();
            boolean errori = convalida(vistaFormEsame);
            if (errori) {
                return;
            }
            Studente studente = (Studente) Applicazione.getInstance().getModello().getBean(Costanti.STUDENTE);
            Esame esame = new Esame();
            esame.setStudenteId(studente.getId());
            esame.setInsegnamento(vistaFormEsame.getCampoInsegnamento().getText().toString());
            esame.setVoto(Integer.parseInt(vistaFormEsame.getCampoVoto().getText().toString()));
            esame.setLode(vistaFormEsame.getCampoLode().isChecked());
            esame.setCrediti(Integer.parseInt(vistaFormEsame.getCampoCrediti().getText().toString()));
            esame.setDataRegistrazione(LocalDate.parse(vistaFormEsame.getCampoData().getText().toString()));
            Log.d(TAG, esame.toString());
            new AsyncTaskAggiungiEsame(esame).execute();
        }

        private boolean convalida(VistaFormEsame vistaFormEsame) {
            //TODO
            return false;
        }
    }
}
