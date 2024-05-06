package it.unibas.mediapesataclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import it.unibas.mediapesataclient.Applicazione;
import it.unibas.mediapesataclient.R;
import it.unibas.mediapesataclient.controllo.AsyncTaskGetEsamiStudente;
import it.unibas.mediapesataclient.controllo.AsyncTaskGetMediaPesata;
import it.unibas.mediapesataclient.modello.Costanti;
import it.unibas.mediapesataclient.modello.Studente;
import it.unibas.mediapesataclient.vista.VistaLogin;
import it.unibas.mediapesataclient.vista.VistaStudente;

public class ActivityStudente extends AppCompatActivity {

    public static final String TAG = ActivityStudente.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studente);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Studente studente = (Studente) Applicazione.getInstance().getModello().getBean(Costanti.STUDENTE);
        new AsyncTaskGetEsamiStudente(studente.getId()).execute();
        new AsyncTaskGetMediaPesata(studente.getId()).execute();
    }

    public VistaStudente getVistaStudente() {
        return (VistaStudente) this.getSupportFragmentManager().findFragmentById(R.id.vistaStudente);
    }

    public void mostraMessaggioErrori(String messaggio) {
        Toast.makeText(this, messaggio, Toast.LENGTH_SHORT).show();
    }

    public void mostraActivityAggiungiEsame() {
        startActivity(new Intent(this, ActivityFormEsame.class));
    }
}
