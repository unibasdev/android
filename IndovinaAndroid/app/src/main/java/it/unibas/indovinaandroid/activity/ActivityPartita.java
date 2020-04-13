package it.unibas.indovinaandroid.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import it.unibas.indovinaandroid.Applicazione;
import it.unibas.indovinaandroid.R;
import it.unibas.indovinaandroid.vista.VistaPartita;

public class ActivityPartita extends AppCompatActivity {

    public static final String TAG = "ActivityPartita";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Applicazione.getInstance().previousSessionDestroyed(savedInstanceState)) {
            Log.w(TAG, "E' stato creato un nuovo contesto. Ricreo l'activity...");
            Applicazione.getInstance().riavviaApplicazione();
            super.onCreate(null);
            return;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partita);
    }

    public VistaPartita getVistaPartita() {
        return (VistaPartita) this.getSupportFragmentManager().findFragmentById(R.id.vistaPartita);
    }

    ////////////////////////// MENU ////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_partita, menu);
        menu.findItem(R.id.menu_visualizza_record).setOnMenuItemClickListener(Applicazione.getInstance().getControlloMenu().getAzioneVisualizzaRecord());
        menu.findItem(R.id.menu_nuova_partita).setOnMenuItemClickListener(Applicazione.getInstance().getControlloMenu().getAzioneNuovaPartita());
        menu.findItem(R.id.menu_interrompi_partita).setOnMenuItemClickListener(Applicazione.getInstance().getControlloMenu().getAzioneInterrompiPartita());
        menu.findItem(R.id.menu_informazioni).setOnMenuItemClickListener(Applicazione.getInstance().getControlloMenu().getAzioneInformazioni());
        return true;
    }

    ////////////////////////// SCHERMI ////////////////////////////

    public void mostraImpostazioni() {
        startActivity(new Intent(this, ActivityImpostazioni.class));
    }

    public void finestraRecord(int record) {
        StringBuffer messaggio = new StringBuffer();
        if (record > 0) {
            messaggio.append(getString(R.string.attualeRecord, record));
        } else {
            messaggio.append(getString(R.string.recordNonStabilito));
        }
        mostraMessaggio(messaggio.toString());
    }

    private void mostraMessaggio(String messaggio) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setMessage(messaggio);
        builder.setPositiveButton(getString(R.string.ok), null);
        AlertDialog alert = builder.create();
        alert.show();
    }

}
