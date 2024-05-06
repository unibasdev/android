package it.unibas.mediapesataclient.vista;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.fragment.app.Fragment;

import java.time.LocalDate;
import java.time.LocalDateTime;

import it.unibas.mediapesataclient.Applicazione;
import it.unibas.mediapesataclient.R;

public class VistaFormEsame extends Fragment {
    private ProgressDialog progressDialog;
    private EditText campoInsegnamento;
    private EditText campoVoto;
    private Switch campoLode;
    private EditText campoCrediti;
    private EditText campoData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.vista_form_esame, container, false);
        this.campoInsegnamento = vista.findViewById(R.id.campoInsegnamento);
        this.campoVoto = vista.findViewById(R.id.campoVoto);
        this.campoLode = vista.findViewById(R.id.campoLode);
        this.campoCrediti = vista.findViewById(R.id.campoCrediti);
        this.campoData = vista.findViewById(R.id.campoData);
        this.campoData.setText(LocalDate.now().toString());
        Button bottoneAggiungi = vista.findViewById(R.id.bottoneAggiungi);
        bottoneAggiungi.setOnClickListener(Applicazione.getInstance().getControlloFormEsame().getAzioneAggiungiEsame());
        return vista;
    }

    public EditText getCampoInsegnamento() {
        return campoInsegnamento;
    }

    public EditText getCampoVoto() {
        return campoVoto;
    }

    public Switch getCampoLode() {
        return campoLode;
    }

    public EditText getCampoCrediti() {
        return campoCrediti;
    }

    public EditText getCampoData() {
        return campoData;
    }

    public void mostraFinestraCaricamento() {
        progressDialog = ProgressDialog.show(Applicazione.getInstance().getCurrentActivity(), "Caricamento", "Caricamento in corso. Attendi...", true);
        progressDialog.show();
    }

    public void chiudiFinestraCaricamento() {
        progressDialog.dismiss();
    }


}
