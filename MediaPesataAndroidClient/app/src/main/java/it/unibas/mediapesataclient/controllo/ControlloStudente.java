package it.unibas.mediapesataclient.controllo;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import it.unibas.mediapesataclient.Applicazione;
import it.unibas.mediapesataclient.activity.ActivityStudente;
import it.unibas.mediapesataclient.modello.Esame;

public class ControlloStudente {

    private View.OnClickListener azioneAggiungiEsame = new AzioneAggiungiEsame();
    private AdapterView.OnItemLongClickListener azioneEliminaEsame = new AzioneEliminaEsame();

    public View.OnClickListener getAzioneAggiungiEsame() {
        return azioneAggiungiEsame;
    }

    public AdapterView.OnItemLongClickListener getAzioneEliminaEsame() {
        return azioneEliminaEsame;
    }

    private class AzioneAggiungiEsame implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            ActivityStudente activityStudente = (ActivityStudente) Applicazione.getInstance().getCurrentActivity();
            activityStudente.mostraActivityAggiungiEsame();
        }
    }

    private class AzioneEliminaEsame implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            Esame esame = (Esame) parent.getItemAtPosition(position);
            new AsyncTaskEliminaEsame(esame).execute();
            return false;
        }
    }
}
