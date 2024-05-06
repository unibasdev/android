package it.unibas.mediapesataclient.vista;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.NumberFormat;

import it.unibas.mediapesataclient.Applicazione;
import it.unibas.mediapesataclient.R;
import it.unibas.mediapesataclient.modello.Costanti;
import it.unibas.mediapesataclient.modello.Studente;

public class VistaStudente extends Fragment {
    private ProgressDialog progressDialog;
    private TextView labelMatricola;
    private TextView labelAnno;
    private TextView labelNomeCognome;
    private TextView labelMediaPesata;
    private ListView listaEsami;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.vista_studente, container, false);
        labelMatricola = vista.findViewById(R.id.labelMatricola);
        labelAnno = vista.findViewById(R.id.labelAnno);
        labelNomeCognome = vista.findViewById(R.id.labelNomeCognome);
        labelMediaPesata = vista.findViewById(R.id.labelMediaPesata);
        listaEsami = vista.findViewById(R.id.listaEsami);
        Button bottoneAggiungiEsame = vista.findViewById(R.id.bottoneAggiungiEsame);
        bottoneAggiungiEsame.setOnClickListener(Applicazione.getInstance().getControlloStudente().getAzioneAggiungiEsame());
        listaEsami.setOnItemLongClickListener(Applicazione.getInstance().getControlloStudente().getAzioneEliminaEsame());
        return vista;
    }

    public void mostraFinestraCaricamento() {
        progressDialog = ProgressDialog.show(Applicazione.getInstance().getCurrentActivity(), "Caricamento", "Caricamento in corso. Attendi...", true);
        progressDialog.show();
    }

    public void chiudiFinestraCaricamento() {
        progressDialog.dismiss();
    }

    public void onResume(){
        super.onResume();
        this.aggiornaDati();
    }

    public void aggiornaDati(){
        Studente studente = (Studente) Applicazione.getInstance().getModello().getBean(Costanti.STUDENTE);
        labelMatricola.setText(studente.getMatricola() + "");
        labelAnno.setText(studente.getAnnoIscrizione() + "");
        labelNomeCognome.setText(studente.getNome() + " " + studente.getCognome());
        if(studente.getMediaPesata() == null) {
            labelMediaPesata.setText("Media Pesata: ...");
        }else {
            NumberFormat numberFormat = NumberFormat.getNumberInstance();
            numberFormat.setMaximumFractionDigits(3);
            labelMediaPesata.setText("Media Pesata: " + numberFormat.format(studente.getMediaPesata()));
        }
        listaEsami.setAdapter(new AdapterEsami(studente.getListaEsami()));
    }

}
