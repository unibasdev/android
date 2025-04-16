package it.unibas.mediapesataandroid.vista;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import it.unibas.mediapesataandroid.Applicazione;
import it.unibas.mediapesataandroid.R;
import it.unibas.mediapesataandroid.modello.EBean;
import it.unibas.mediapesataandroid.modello.ModelloPersistente;
import it.unibas.mediapesataandroid.modello.Studente;

public class VistaStudente extends Fragment {

    public static final String TAG = VistaStudente.class.getName();

    private TextView testoCognome;
    private TextView testoNome;
    private TextView testoMatricola;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.vista_studente, container, false);
        testoCognome = (TextView) vista.findViewById(R.id.testoCognome);
        testoNome = (TextView) vista.findViewById(R.id.testoNome);
        testoMatricola = (TextView) vista.findViewById(R.id.testoMatricola);
        return vista;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.aggiornaDatiStudente();
    }

    ////////////////////////// SCHERMI ////////////////////////////
    public void aggiornaDatiStudente() {
        ModelloPersistente modello = Applicazione.getInstance().getModello();
        Studente studente = (Studente) modello.getPersistentBean(EBean.STUDENTE, Studente.class);
        testoCognome.setText(studente.getCognome());
        testoNome.setText(studente.getNome());
        testoMatricola.setText(studente.getMatricola() + "");
    }
}
