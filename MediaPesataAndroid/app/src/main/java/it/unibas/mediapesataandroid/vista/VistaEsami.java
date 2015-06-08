package it.unibas.mediapesataandroid.vista;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;

import it.unibas.mediapesataandroid.Applicazione;
import it.unibas.mediapesataandroid.Costanti;
import it.unibas.mediapesataandroid.R;
import it.unibas.mediapesataandroid.modello.ModelloListaEsami;
import it.unibas.mediapesataandroid.modello.ModelloPersistente;
import it.unibas.mediapesataandroid.modello.Studente;

public class VistaEsami extends Fragment {

    public static final String TAG = VistaEsami.class.getName();

    private TextView testoCreditiTotali;
    private TextView testoMediaVoto30mi;
    private TextView testaMediaVoto110mi;
    private ListView listaEsami;
    private FloatingActionButton bottoneAggiungiEsame;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, TAG + " onCreateView");
        View vista = inflater.inflate(R.layout.vista_esami, container, false);
        testoCreditiTotali = (TextView) vista.findViewById(R.id.testoCreditiTotali);
        testoMediaVoto30mi = (TextView) vista.findViewById(R.id.testoMediaVoto30mi);
        testaMediaVoto110mi = (TextView) vista.findViewById(R.id.testaMediaVoto110mi);
        listaEsami = (ListView) vista.findViewById(R.id.listaEsami);
        bottoneAggiungiEsame = (FloatingActionButton) vista.findViewById(R.id.bottoneAggiungiEsame);
        bottoneAggiungiEsame.setOnClickListener(Applicazione.getInstance().getControlloPrincipale().getAzioneInserisciEsame());
        listaEsami.setOnItemClickListener(Applicazione.getInstance().getControlloPrincipale().getAzioneSelezionaEsame());
        listaEsami.setOnItemLongClickListener(Applicazione.getInstance().getControlloPrincipale().getAzioneMostraOperazioniEsame());
        this.aggiornaVista();
        return vista;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.aggiornaEsami();
    }

    ////////////////////////// SCHERMI ////////////////////////////

    public void aggiornaVista() {
//        this.listaEsami.setAdapter(new ArrayAdapter<Esame>(getActivity(), R.layout.riga_esami, R.id.rigaTestoInsegnamento, studente.getListaEsami()));
        this.listaEsami.setAdapter(new ModelloListaEsami());
    }

    public void aggiornaEsami() {
        ModelloPersistente modello = Applicazione.getInstance().getModello();
        Studente studente = (Studente) modello.getPersistentBean(Costanti.STUDENTE, Studente.class);
        calcolaMedia(studente);
        aggiornaListaEsami(studente);
    }

    private void calcolaMedia(Studente studente) {
        if (studente != null && studente.getNumeroEsami() != 0) {
            DecimalFormat formattatore = new DecimalFormat("###.##");
            this.testoCreditiTotali.setText(getString(R.string.StringaCreditiTotali) + " " + studente.getCreditiTotali());
            this.testoMediaVoto30mi.setText(getString(R.string.StringaMedia30mi) + " " + formattatore.format(studente.getMedia30mi()));
            this.testaMediaVoto110mi.setText(getString(R.string.StringaMedia110mi) + " " + formattatore.format(studente.getMedia110mi()));
        } else {
            this.testoCreditiTotali.setText(getString(R.string.StringaCreditiTotali) + " 0");
            this.testoMediaVoto30mi.setText(getString(R.string.StringaMedia30mi) + " 0.0");
            this.testaMediaVoto110mi.setText(getString(R.string.StringaMedia110mi) + " 0.0");
        }
    }

    private void aggiornaListaEsami(Studente studente) {
        Log.d(TAG, studente.toString());
        ModelloListaEsami adapter = (ModelloListaEsami) this.listaEsami.getAdapter();
        adapter.updateContent();
    }
}
