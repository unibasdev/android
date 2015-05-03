package it.unibas.morracineseandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;

import it.unibas.morracineseandroid.Applicazione;
import it.unibas.morracineseandroid.R;
import it.unibas.morracineseandroid.vista.VistaPrincipale;

public class ActivityPrincipale extends ActionBarActivity {

    public static final String TAG = ActivityPrincipale.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Applicazione.getInstance().previousSessionDestroyed(savedInstanceState)) {
            Log.w(TAG, "E' stato creato un nuovo contesto. Ricreo l'activity...");
            Applicazione.getInstance().riavviaApplicazione();
            super.onCreate(null);
            return;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principale);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principale, menu);
        menu.findItem(R.id.menuNuovaPartita).setOnMenuItemClickListener(Applicazione.getInstance().getControlloMenu().getAzioneIniziaPartita());
        menu.findItem(R.id.menuInterrompiPartita).setOnMenuItemClickListener(Applicazione.getInstance().getControlloMenu().getAzioneInterrompiPartita());
        menu.findItem(R.id.menuInformazioni).setOnMenuItemClickListener(Applicazione.getInstance().getControlloMenu().getAzioneInformazioni());
        return true;
    }

    public VistaPrincipale getVistaPrincipale() {
        return (VistaPrincipale)this.getSupportFragmentManager().findFragmentById(R.id.vistaPrincipale);
    }

    public void mostraImpostazioni(){
        startActivity(new Intent(this, ActivityImpostazioni.class));
    }
}
