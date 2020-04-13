package it.unibas.indovinaandroid.vista;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import it.unibas.indovinaandroid.Applicazione;
import it.unibas.indovinaandroid.Costanti;
import it.unibas.indovinaandroid.R;
import it.unibas.indovinaandroid.modello.Modello;
import it.unibas.indovinaandroid.modello.Partita;
import it.unibas.indovinaandroid.modello.Record;

public class VistaPartita extends Fragment {

    public static final String TAG = "VistaPartita";

    private EditText textTentativo;
    private TextView labelMessaggio1;
    private TextView labelMessaggio2;
    private TextView labelNumeroTentativi;
    private FloatingActionButton bottoneTentativo;
    private int durataAnimazione;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Tutte le proprieta' devono essere inizializzate qui
        View vista = inflater.inflate(R.layout.vista_partita, container, false);
        textTentativo = (EditText) vista.findViewById(R.id.testoTentativo);
        labelMessaggio1 = (TextView) vista.findViewById(R.id.labelMessaggio1);
        labelMessaggio2 = (TextView) vista.findViewById(R.id.labelMessaggio2);
        labelNumeroTentativi = (TextView) vista.findViewById(R.id.labelNumeroTentativi);
        bottoneTentativo = (FloatingActionButton) vista.findViewById(R.id.bottoneTentativo);
        inizializzaAzioni();
        schermoNuovaPartita();
//        durataAnimazione = getResources().getInteger(android.R.integer.config_shortAnimTime);
        durataAnimazione = getResources().getInteger(android.R.integer.config_longAnimTime);
        return vista;
    }


    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState == null) {
            return;
        }
        if (savedInstanceState.getBoolean(Costanti.STATOBOTTONI)) {
            this.abilitaBottoniStatoPartita();
        } else {
            this.abilitaBottoniStatoNoPartita();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(Costanti.STATOBOTTONI, this.isStatoBottoniPartitaInCorso());
    }

    private void inizializzaAzioni() {
        bottoneTentativo.setOnClickListener(Applicazione.getInstance().getControlloPartita().getAzioneTentativo());
    }

    public String getTestoTentativo() {
        return textTentativo.getText().toString();
    }

    public void setErroreTentativo(String messaggio) {
        textTentativo.setError(messaggio);
    }

    /////////////////// SCHERMI
    public void schermoNuovaPartita() {
        Modello modello = Applicazione.getInstance().getModello();
        Partita partita = (Partita) modello.getBean(Costanti.PARTITA);
        this.textTentativo.setText("");
        this.labelMessaggio1.setText(getString(R.string.messaggio_benvenuto, partita.getNome()));
        this.labelMessaggio2.setText(getString(R.string.tentativiEffettuati));
        this.labelNumeroTentativi.setText(partita.getNumeroDiTentativi() + "");
        abilitaBottoniStatoPartita();
    }

    public void schermoFinePartita() {
        Modello modello = Applicazione.getInstance().getModello();
        Partita partita = (Partita) modello.getBean(Costanti.PARTITA);
        Record record = (Record) modello.getBean(Costanti.RECORD);
        this.labelMessaggio1.setText(getString(R.string.numeroIndovinato, partita.getNome(), partita.getNumeroDiTentativi()));
        if (record.isUguagliato()) {
            this.labelMessaggio2.setText(getString(R.string.recordUguagliato));
        } else if (record.isNuovoRecord()) {
            this.labelMessaggio2.setText(getString(R.string.nuovoRecord));
        }
        this.labelNumeroTentativi.setText("");
        abilitaBottoniStatoNoPartita();
    }

    public void schermoPartitaInterrotta() {
        Modello modello = Applicazione.getInstance().getModello();
        Partita partita = (Partita) modello.getBean(Costanti.PARTITA);
        this.textTentativo.setText("");
        this.labelMessaggio1.setText(getString(R.string.numeroNonIndovinato, partita.getNome(), partita.getNumeroDaIndovinare()));
        this.labelNumeroTentativi.setText(partita.getNumeroDiTentativi() + "");
        abilitaBottoniStatoNoPartita();
    }

    public void schermoAggiornaPartita() {
        Modello modello = Applicazione.getInstance().getModello();
        Partita partita = (Partita) modello.getBean(Costanti.PARTITA);
        this.textTentativo.setText("");
        this.labelMessaggio1.setText(partita.getSuggerimento());
        this.labelMessaggio2.setText(getString(R.string.tentativiEffettuati));
        ///ANIMAZIONE
        crossFade(this.labelNumeroTentativi, partita.getNumeroDiTentativi() + "");
    }

    private void crossFade(final TextView componente, final String nuovoValore) {
        componente.setAlpha(1f);
        componente.animate()
                .alpha(0f)
                .setDuration(durataAnimazione)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        componente.setText(nuovoValore);
                        componente.animate()
                                .alpha(1f)
                                .setDuration(durataAnimazione)
                                .setListener(null);
                    }
                });

    }

    public boolean isStatoBottoniPartitaInCorso() {
        return bottoneTentativo.isEnabled();
    }

    public void abilitaBottoniStatoPartita() {
        this.bottoneTentativo.setEnabled(true);
        this.textTentativo.setEnabled(true);
    }

    public void abilitaBottoniStatoNoPartita() {
        this.bottoneTentativo.setEnabled(false);
        this.textTentativo.setEnabled(false);
    }
}