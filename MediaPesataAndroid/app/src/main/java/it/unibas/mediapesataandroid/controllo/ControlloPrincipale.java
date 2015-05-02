package it.unibas.mediapesataandroid.controllo;

import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import it.unibas.mediapesataandroid.Applicazione;
import it.unibas.mediapesataandroid.Costanti;
import it.unibas.mediapesataandroid.activity.ActivityPrincipale;
import it.unibas.mediapesataandroid.modello.Esame;
import it.unibas.mediapesataandroid.modello.ModelloPersistente;
import it.unibas.mediapesataandroid.modello.Studente;

public class ControlloPrincipale {

    private final View.OnClickListener azioneInserisciEsame = new AzioneInserisciEsame();
    private final AdapterView.OnItemLongClickListener azioneMostraOperazioniEsame = new AzioneMostraOperazioniEsame();
    private final DialogInterface.OnClickListener azioneEseguiOperazioneEsame = new AzioneEseguiOperazioneEsame();
    private final AdapterView.OnItemClickListener azioneSelezionaEsame = new AzioneSelezionaEsame();
    private final DialogInterface.OnClickListener azioneEliminaEsame = new AzioneEliminaEsame();

    public View.OnClickListener getAzioneInserisciEsame() {
        return azioneInserisciEsame;
    }

    public AdapterView.OnItemClickListener getAzioneSelezionaEsame() {
        return azioneSelezionaEsame;
    }

    public AdapterView.OnItemLongClickListener getAzioneMostraOperazioniEsame() {
        return azioneMostraOperazioniEsame;
    }

    public DialogInterface.OnClickListener getAzioneEseguiOperazioneEsame() {
        return azioneEseguiOperazioneEsame;
    }

    public DialogInterface.OnClickListener getAzioneEliminaEsame() {
        return azioneEliminaEsame;
    }

    ///////////////////////////////////////////////////////////////////////////////////
    public class AzioneInserisciEsame implements View.OnClickListener {

        private final String TAG = AzioneInserisciEsame.class.getName();

        @Override
        public void onClick(View view) {
            Log.d(TAG, "Eseguo AzioneInserisciEsame");
            ModelloPersistente modello = Applicazione.getInstance().getModello();
            modello.saveBean(Costanti.ESAME, null);
            ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
            activityPrincipale.schermoFormEsame();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////
    public class AzioneSelezionaEsame implements AdapterView.OnItemClickListener {

        private final String TAG = AzioneSelezionaEsame.class.getName();

        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            Log.d(TAG, "Eseguo AzioneSelezionaEsame");
            ModelloPersistente modello = Applicazione.getInstance().getModello();
            Studente studente = (Studente) modello.getPersistentBean(Costanti.STUDENTE, Studente.class);
            Esame esameSelezionato = studente.getEsame(position);
            modello.saveBean(Costanti.ESAME, esameSelezionato);
            ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
            activityPrincipale.schermoFormEsame();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////
    public class AzioneMostraOperazioniEsame implements AdapterView.OnItemLongClickListener {

        private final String TAG = AzioneMostraOperazioniEsame.class.getName();

        @Override
        public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
            Log.d(TAG, "Eseguo AzioneMostraOperazioniEsame");
            Applicazione.getInstance().getModello().saveBean(Costanti.POSIZIONE, position);
            ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
            activityPrincipale.finestraOperazioniEsame();
            return true;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////
    public class AzioneEseguiOperazioneEsame implements DialogInterface.OnClickListener {

        private final String TAG = AzioneEseguiOperazioneEsame.class.getName();

        @Override
        public void onClick(DialogInterface dialogInterface, int idOperazione) {
            Log.d(TAG, "Eseguo AzioneEseguiOperazioneEsame sull'operazione " + idOperazione);
            if (idOperazione == 0) {
                ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
                activityPrincipale.finestraConfermaEliminazione(azioneEliminaEsame);
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////
    public class AzioneEliminaEsame implements DialogInterface.OnClickListener {

        private final String TAG = AzioneEliminaEsame.class.getName();

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            Log.d(TAG, "Eseguo AzioneEliminaEsame");
            ModelloPersistente modello = Applicazione.getInstance().getModello();
            Studente studente = (Studente) modello.getPersistentBean(Costanti.STUDENTE, Studente.class);
            Integer posizione = (Integer) Applicazione.getInstance().getModello().getPersistentBean(Costanti.POSIZIONE, Integer.class);
            studente.eliminaEsame(posizione);
            modello.saveBean(Costanti.STUDENTE, studente);
            ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
            activityPrincipale.getVistaEsami().aggiornaEsami();
        }
    }

}
