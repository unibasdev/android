package it.unibas.indovinaandroid.vista;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import it.unibas.indovinaandroid.Applicazione;
import it.unibas.indovinaandroid.R;

public class VistaIniziale extends Fragment {

    public static final String TAG = "VistaIniziale";

    private EditText textNome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.vista_iniziale, container, false);
        textNome = (EditText) vista.findViewById(R.id.testoNome);
        FloatingActionButton bottoneInizia = (FloatingActionButton) vista.findViewById(R.id.bottoneInizia);
        bottoneInizia.setOnClickListener(Applicazione.getInstance().getControlloIniziale().getAzioneIniziaGioco());
        TextView textLink = (TextView) vista.findViewById(R.id.testoLink);
        textLink.setOnClickListener(Applicazione.getInstance().getControlloIniziale().getAzioneLink());
        return vista;
    }

    public String getNome() {
        return textNome.getText().toString();
    }


    public void setErroreNome(String messaggio) {
        textNome.setError(messaggio);
    }
}