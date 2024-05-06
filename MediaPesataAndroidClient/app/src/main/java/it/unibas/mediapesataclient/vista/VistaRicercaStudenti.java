package it.unibas.mediapesataclient.vista;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.List;

import it.unibas.mediapesataclient.Applicazione;
import it.unibas.mediapesataclient.R;
import it.unibas.mediapesataclient.modello.Costanti;
import it.unibas.mediapesataclient.modello.Studente;

public class VistaRicercaStudenti extends Fragment {
    private ProgressDialog progressDialog;
    private EditText campoNome;
    private EditText campoCognome;
    private EditText campoAnno;
    private ListView listaStudenti;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.vista_ricerca_studenti, container, false);
        this.campoNome = vista.findViewById(R.id.campoNome);
        this.campoCognome = vista.findViewById(R.id.campoCognome);
        this.campoAnno = vista.findViewById(R.id.campoAnno);
        this.listaStudenti = vista.findViewById(R.id.listaStudenti);
        Button bottoneCerca = vista.findViewById(R.id.bottoneCerca);
        bottoneCerca.setOnClickListener(Applicazione.getInstance().getControlloRicercaStudenti().getAzioneCerca());
        this.listaStudenti.setOnItemClickListener(Applicazione.getInstance().getControlloRicercaStudenti().getAzioneSelezionaStudente());
        return vista;
    }

    public String getCampoNome() {
        return campoNome.getText().toString();
    }

    public String getCampoCognome() {
        return campoCognome.getText().toString();
    }

    public String getCampoAnno() {
        return campoAnno.getText().toString();
    }

    public void mostraFinestraCaricamento() {
        progressDialog = ProgressDialog.show(Applicazione.getInstance().getCurrentActivity(), "Caricamento", "Caricamento in corso. Attendi...", true);
        progressDialog.show();
    }

    public void chiudiFinestraCaricamento() {
        progressDialog.dismiss();
    }

    public void aggiornaDati() {
        List<Studente> studenti = (List<Studente>) Applicazione.getInstance().getModello().getBean(Costanti.STUDENTI);
        this.listaStudenti.setAdapter(new AdapterStudenti(studenti));
    }
}
