package it.unibas.wordle.controllo;

import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.unibas.wordle.Applicazione;
import it.unibas.wordle.activity.ActivityPrincipale;
import it.unibas.wordle.modello.Archivio;
import it.unibas.wordle.modello.Costanti;
import it.unibas.wordle.modello.Partita;
import it.unibas.wordle.modello.Suggerimento;
import it.unibas.wordle.modello.Tentativo;
import it.unibas.wordle.vista.VistaPrincipale;

public class ControlloPrincipale {

    private String TAG = ControlloPrincipale.class.getSimpleName();

    private MenuItem.OnMenuItemClickListener azioneNuovaPartita = new AzioneNuovaPartita();
    private MenuItem.OnMenuItemClickListener azioneInterrompiPartita = new AzioneInterrompiPartita();
    private TextView.OnEditorActionListener azioneSottomettiTentativo = new AzioneSottomettiTentativo();

    public TextView.OnEditorActionListener getAzioneSottomettiTentativo() {
        return azioneSottomettiTentativo;
    }

    public MenuItem.OnMenuItemClickListener getAzioneNuovaPartita() {
        return azioneNuovaPartita;
    }

    public MenuItem.OnMenuItemClickListener getAzioneInterrompiPartita() {
        return azioneInterrompiPartita;
    }

    private class AzioneSottomettiTentativo implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            Log.i(TAG, "Sottomesso un nuovo tentativo");
            ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
            VistaPrincipale vistaPrincipale = activityPrincipale.getVistaPrincipale();
            boolean errori = convalida(vistaPrincipale);
            if (errori) {
                return false;
            }
            Partita partita = (Partita) Applicazione.getInstance().getModello().getBean(Costanti.PARTITA);
            String parolaTentata = (vistaPrincipale.getLettera1().getText().toString()
                    + vistaPrincipale.getLettera2().getText().toString()
                    + vistaPrincipale.getLettera3().getText().toString()
                    + vistaPrincipale.getLettera4().getText().toString()
                    + vistaPrincipale.getLettera5().getText().toString()).toUpperCase();
            Archivio archivio = (Archivio) Applicazione.getInstance().getModello().getBean(Costanti.ARCHIVIO);
            if (!archivio.getArchivio().contains(parolaTentata)) {
                activityPrincipale.mostraMessaggio("Parola inesistente: " + parolaTentata);
                vistaPrincipale.getLettera1().requestFocus();
                return false;
            }
            Tentativo tentativo = Applicazione.getInstance().getOperatore().valutaTentativo(parolaTentata, partita);
            partita.aggiungiTentativo(tentativo);
            if (tentativo.isIndovinato()) {
                activityPrincipale.mostraMessaggio("Congratulazioni, hai indivonato in " + partita.getNumeroTentativi() + " tentativi!");
                activityPrincipale.abilitaAzioneNuovaPartita();
                activityPrincipale.disabilitaAzioneInterrompiPartita();
            } else if (partita.isConclusa()) {
                activityPrincipale.mostraMessaggio("Mi dispiace, non hai indovinato. La parola segreta era " + partita.getParola());
                activityPrincipale.abilitaAzioneNuovaPartita();
                activityPrincipale.disabilitaAzioneInterrompiPartita();
            }
            vistaPrincipale.aggiornaDati();
            View currentFocus = activityPrincipale.getCurrentFocus();
            if(currentFocus != null) currentFocus.clearFocus();
            return false;
        }

        private boolean convalida(VistaPrincipale vistaPrincipale) {
            boolean errori = false;
            if (vistaPrincipale.getLettera1().getText().toString().trim().isEmpty()) {
                vistaPrincipale.setErroreLettera1("Valore obbligatorio");
                errori = true;
            }
            if (vistaPrincipale.getLettera2().getText().toString().trim().isEmpty()) {
                vistaPrincipale.setErroreLettera2("Valore obbligatorio");
                errori = true;
            }
            if (vistaPrincipale.getLettera3().getText().toString().trim().isEmpty()) {
                vistaPrincipale.setErroreLettera3("Valore obbligatorio");
                errori = true;
            }
            if (vistaPrincipale.getLettera4().getText().toString().trim().isEmpty()) {
                vistaPrincipale.setErroreLettera4("Valore obbligatorio");
                errori = true;
            }
            if (vistaPrincipale.getLettera5().getText().toString().trim().isEmpty()) {
                vistaPrincipale.setErroreLettera5("Valore obbligatorio");
                errori = true;
            }
            return errori;
        }
    }

    private class AzioneNuovaPartita implements MenuItem.OnMenuItemClickListener {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            new AsyncTaskParolaCasuale().execute();
            return false;
        }
    }

    private class AzioneInterrompiPartita implements MenuItem.OnMenuItemClickListener {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
            activityPrincipale.abilitaAzioneNuovaPartita();
            activityPrincipale.disabilitaAzioneInterrompiPartita();
            Partita partita = (Partita) Applicazione.getInstance().getModello().getBean(Costanti.PARTITA);
            activityPrincipale.mostraMessaggio("Mi dispiace, non hai indovinato. La parola segreta era " + partita.getParola());
            Applicazione.getInstance().getModello().putBean(Costanti.PARTITA, null);
            activityPrincipale.getVistaPrincipale().aggiornaDati();
            return false;
        }
    }
}
