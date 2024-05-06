package it.unibas.mediapesataclient.controllo;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import it.unibas.mediapesataclient.Applicazione;
import it.unibas.mediapesataclient.activity.ActivityRicercaStudenti;
import it.unibas.mediapesataclient.modello.Costanti;
import it.unibas.mediapesataclient.modello.Studente;
import it.unibas.mediapesataclient.vista.VistaRicercaStudenti;

public class ControlloRicercaStudenti {

    private View.OnClickListener azioneCerca = new AzioneCerca();
    private AdapterView.OnItemClickListener azioneSelezionaStudente = new AzioneSelezioneStudente();

    public View.OnClickListener getAzioneCerca() {
        return azioneCerca;
    }

    public AdapterView.OnItemClickListener getAzioneSelezionaStudente() {
        return azioneSelezionaStudente;
    }

    private class AzioneCerca implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            ActivityRicercaStudenti activityRicercaStudenti = (ActivityRicercaStudenti) Applicazione.getInstance().getCurrentActivity();
            VistaRicercaStudenti vistaRicercaStudenti = activityRicercaStudenti.getVistaRicercaStudenti();
            new AsyncTaskRicercaStudenti(vistaRicercaStudenti.getCampoNome(), vistaRicercaStudenti.getCampoCognome(), vistaRicercaStudenti.getCampoAnno()).execute();
        }
    }

    private class AzioneSelezioneStudente implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Studente studente = (Studente) parent.getItemAtPosition(position);
            Applicazione.getInstance().getModello().putBean(Costanti.STUDENTE, studente);
            ActivityRicercaStudenti activityRicercaStudenti = (ActivityRicercaStudenti) Applicazione.getInstance().getCurrentActivity();
            activityRicercaStudenti.mostraActivityStudente();
        }
    }
}
