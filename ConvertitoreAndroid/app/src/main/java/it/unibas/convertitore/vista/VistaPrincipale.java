package it.unibas.convertitore.vista;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

import it.unibas.convertitore.Applicazione;
import it.unibas.convertitore.Costanti;
import it.unibas.convertitore.R;

public class VistaPrincipale extends Fragment {

    public static final String TAG = VistaPrincipale.class.getSimpleName();

    private EditText editTextVoto30mi;
    private EditText editTextVotoLaurea;
    private TextView textViewMessaggioConferma;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.vista_principale, container, false);
        this.editTextVoto30mi = fragment.findViewById(R.id.editTextVoto30mi);
        this.editTextVotoLaurea = fragment.findViewById(R.id.editTextVoto110mi);
        Button buttonConverti = fragment.findViewById(R.id.buttonConverti);
        buttonConverti.setOnClickListener(Applicazione.getInstance().getControlloPrincipale().getAzioneConverti());
        Button buttonReset = fragment.findViewById(R.id.buttonReset);
        buttonReset.setOnClickListener(Applicazione.getInstance().getControlloPrincipale().getAzioneReset());
        this.textViewMessaggioConferma = fragment.findViewById(R.id.textViewMessaggioConferma);
        Log.i(TAG, "VistaPrincipale.onCreateView()");

        return fragment;
    }

    public String getVoto30mi() {
        return this.editTextVoto30mi.getText().toString();
    }

    public String getVotoLaurea() {
        return this.editTextVotoLaurea.getText().toString();
    }

    public void setErroreVoto30mi(String errore){
        this.editTextVoto30mi.setError(errore);
    }

    public void setErroreVoto110mi(String errore){
        this.editTextVotoLaurea.setError(errore);
    }

    public void cambiaVoto30mi(double voto, int maxVotoLaurea) {
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMaximumFractionDigits(2);
        this.editTextVoto30mi.setText(formatter.format(voto) + "/" + Costanti.MAX_VOTO_ESAME);
        this.textViewMessaggioConferma.setText(getString(R.string.conversioneEffettuata, maxVotoLaurea, Costanti.MAX_VOTO_ESAME));
    }

    public void cambiaVoto110mi(double voto, int maxVotoLaurea) {
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMaximumFractionDigits(2);
        this.editTextVotoLaurea.setText(formatter.format(voto) + "/" + maxVotoLaurea);
        this.textViewMessaggioConferma.setText(getString(R.string.conversioneEffettuata, Costanti.MAX_VOTO_ESAME, maxVotoLaurea));
    }

    public void pulisci() {
        this.editTextVotoLaurea.setText("");
        this.editTextVoto30mi.setText("");
        this.textViewMessaggioConferma.setText("");
    }
}
