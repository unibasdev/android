package it.unibas.mediapesataandroid.vista;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import it.unibas.mediapesataandroid.Applicazione;
import it.unibas.mediapesataandroid.Costanti;
import it.unibas.mediapesataandroid.R;
import it.unibas.mediapesataandroid.modello.ModelloPersistente;
import it.unibas.mediapesataandroid.modello.Studente;

public class VistaFormStudente extends Fragment {

    public static final String TAG = VistaFormStudente.class.getName();

    private EditText testoCognome;
    private EditText testoNome;
    private EditText testoMatricola;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.vista_form_studente, container, false);
        testoCognome = (EditText) vista.findViewById(R.id.testoCognome);
        testoNome = (EditText) vista.findViewById(R.id.testoNome);
        testoMatricola = (EditText) vista.findViewById(R.id.testoMatricola);
        inizializzaCampi();
        return vista;
    }

    private void inizializzaCampi() {
        ModelloPersistente modello = Applicazione.getInstance().getModello();
        Studente studente = (Studente) modello.getPersistentBean(Costanti.STUDENTE, Studente.class);
        if (studente == null) {
            return;
        }
        testoCognome.setText(studente.getCognome());
        testoNome.setText(studente.getNome());
        testoMatricola.setText(studente.getMatricola() + "");
    }


    public String getNome() {
        return testoNome.getText().toString();
    }


    public String getCognome() {
        return testoCognome.getText().toString();
    }


    public String getMatricola() {
        return testoMatricola.getText().toString();
    }


    public void setErroreNome(String messaggio) {
        testoNome.setError(messaggio);
    }


    public void setErroreCognome(String messaggio) {
        testoCognome.setError(messaggio);
    }


    public void setErroreMatricola(String messaggio) {
        testoMatricola.setError(messaggio);
    }
}
