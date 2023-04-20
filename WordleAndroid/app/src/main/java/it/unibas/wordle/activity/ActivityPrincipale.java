package it.unibas.wordle.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import it.unibas.wordle.Applicazione;
import it.unibas.wordle.R;
import it.unibas.wordle.controllo.AsyncTaskDownloadArchivio;
import it.unibas.wordle.modello.Costanti;
import it.unibas.wordle.vista.VistaPrincipale;

public class ActivityPrincipale extends AppCompatActivity {

    public static final String TAG = ActivityPrincipale.class.getSimpleName();
    private MenuItem menuNuovaPartita;
    private MenuItem menuInterrompiPartita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principale);
    }

    @Override
    public void onResume(){
        super.onResume();
        new AsyncTaskDownloadArchivio().execute();
    }

    public VistaPrincipale getVistaPrincipale() {
        return (VistaPrincipale) this.getSupportFragmentManager().findFragmentById(R.id.vistaPrincipale);
    }

    public void mostraMessaggio(String messaggio) {
        Toast.makeText(this, messaggio, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principale, menu);
        this.menuNuovaPartita = menu.findItem(R.id.menuNuovaPartita);
        menuNuovaPartita.setOnMenuItemClickListener(Applicazione.getInstance().getControlloPrincipale().getAzioneNuovaPartita());
        this.menuInterrompiPartita = menu.findItem(R.id.menuInterrompi);
        menuInterrompiPartita.setOnMenuItemClickListener(Applicazione.getInstance().getControlloPrincipale().getAzioneInterrompiPartita());
        return true;
    }

    public void abilitaAzioneNuovaPartita() {
        this.menuNuovaPartita.setEnabled(true);
    }

    public void abilitaAzioneInterrompiPartita() {
        this.menuInterrompiPartita.setEnabled(true);
    }

    public void disabilitaAzioneNuovaPartita() {
        this.menuNuovaPartita.setEnabled(false);
    }

    public void disabilitaAzioneInterrompiPartita() {
        this.menuInterrompiPartita.setEnabled(false);
    }
}
