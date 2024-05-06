package it.unibas.mediapesataclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import it.unibas.mediapesataclient.R;
import it.unibas.mediapesataclient.controllo.AsyncTaskRicercaStudenti;
import it.unibas.mediapesataclient.vista.VistaLogin;
import it.unibas.mediapesataclient.vista.VistaRicercaStudenti;

public class ActivityRicercaStudenti extends AppCompatActivity {

    public static final String TAG = ActivityRicercaStudenti.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ricerca_studenti);
    }

    public VistaRicercaStudenti getVistaRicercaStudenti() {
        return (VistaRicercaStudenti) this.getSupportFragmentManager().findFragmentById(R.id.vistaRicercaStudenti);
    }

    @Override
    protected void onResume() {
        super.onResume();
        VistaRicercaStudenti vistaRicercaStudenti = getVistaRicercaStudenti();
        new AsyncTaskRicercaStudenti(vistaRicercaStudenti.getCampoNome(), vistaRicercaStudenti.getCampoCognome(), vistaRicercaStudenti.getCampoAnno()).execute();
    }

    public void mostraMessaggioErrori(String messaggio) {
        Toast.makeText(this, messaggio, Toast.LENGTH_SHORT).show();
    }

    public void mostraActivityStudente() {
        startActivity(new Intent(this, ActivityStudente.class));
    }
}
