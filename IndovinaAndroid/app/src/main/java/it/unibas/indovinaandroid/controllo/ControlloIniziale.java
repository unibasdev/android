package it.unibas.indovinaandroid.controllo;

import android.util.Log;
import android.view.View;

import it.unibas.indovinaandroid.Applicazione;
import it.unibas.indovinaandroid.Costanti;
import it.unibas.indovinaandroid.R;
import it.unibas.indovinaandroid.activity.ActivityIniziale;
import it.unibas.indovinaandroid.modello.Modello;
import it.unibas.indovinaandroid.modello.Partita;
import it.unibas.indovinaandroid.modello.Record;
import it.unibas.indovinaandroid.vista.VistaIniziale;

public class ControlloIniziale {

    private final View.OnClickListener azioneIniziaGioco = new AzioneIniziaGioco();
    private final View.OnClickListener azioneLink = new AzioneLink();

    public View.OnClickListener getAzioneIniziaGioco() {
        return azioneIniziaGioco;
    }

    public View.OnClickListener getAzioneLink() {
        return azioneLink;
    }

    public class AzioneIniziaGioco implements View.OnClickListener {

        private static final String TAG = "AzioneIniziaGioco";

        @Override
        public void onClick(View view) {
            Log.d(TAG, "Azione inizia gioco...");
            ActivityIniziale activity = (ActivityIniziale) Applicazione.getInstance().getCurrentActivity();
            VistaIniziale fragment = activity.getVistaIniziale();
            String nome = fragment.getNome();
            if (nome == null || nome.equals("")) {
                fragment.setErroreNome(activity.getString(R.string.errore_nome));
                return;
            }
            Modello modello = Applicazione.getInstance().getModello();
            Partita partita = new Partita();
            partita.setNome(nome);
            Record record = new Record();
            modello.putBean(Costanti.PARTITA, partita);
            modello.putBean(Costanti.RECORD, record);
            activity.schermoAvviaGioco();
        }
    }


    public class AzioneLink implements View.OnClickListener {

        private static final String TAG = "AzioneLink";

        @Override
        public void onClick(View view) {
            ActivityIniziale activityIniziale = (ActivityIniziale) Applicazione.getInstance().getCurrentActivity();
            activityIniziale.avviaBrowser("http://informatica.unibas.it");
        }
    }
}
