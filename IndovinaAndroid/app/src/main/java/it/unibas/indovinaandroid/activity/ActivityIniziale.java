package it.unibas.indovinaandroid.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import it.unibas.indovinaandroid.Applicazione;
import it.unibas.indovinaandroid.R;
import it.unibas.indovinaandroid.vista.VistaIniziale;

public class ActivityIniziale extends AppCompatActivity {

    public static final String TAG = ActivityIniziale.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Applicazione.getInstance().previousSessionDestroyed(savedInstanceState)) {
            Log.w(TAG, "E' stato creato un nuovo contesto. Ricreo l'activity...");
            Applicazione.getInstance().riavviaApplicazione();
            super.onCreate(null);
            return;
        }
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_iniziale);
    }

    public VistaIniziale getVistaIniziale() {
        return (VistaIniziale) this.getSupportFragmentManager().findFragmentById(R.id.vistaIniziale);
    }

    public void schermoAvviaGioco() {
        Intent intent = new Intent(this, ActivityPartita.class);
        this.startActivity(intent);
    }

    public void avviaBrowser(String uri) {
        Intent linkIntent = new Intent();
        linkIntent.setAction(Intent.ACTION_VIEW);
        linkIntent.setData(Uri.parse(uri));
        Applicazione.getInstance().getCurrentActivity().startActivity(linkIntent);
    }
}
