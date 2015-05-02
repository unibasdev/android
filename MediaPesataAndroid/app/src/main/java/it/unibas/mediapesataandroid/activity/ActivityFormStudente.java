package it.unibas.mediapesataandroid.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import it.unibas.mediapesataandroid.Applicazione;
import it.unibas.mediapesataandroid.R;
import it.unibas.mediapesataandroid.vista.VistaFormStudente;

public class ActivityFormStudente extends ActionBarActivity {

    public static final String TAG = "ActivityFormStudente";
    private MenuItem menuItemOK;
    private MenuItem menuItemAnnulla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_form_studente);
        if (savedInstanceState != null) {
            return;
        }
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.containerVistaFormStudente, new VistaFormStudente())
                .commit();
    }

    public VistaFormStudente getVistaFormStudente() {
        return (VistaFormStudente) this.getSupportFragmentManager().findFragmentById(R.id.containerVistaFormStudente);
    }
    ////////////////////////// MENU ////////////////////////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_form_studente, menu);
        menuItemOK = menu.findItem(R.id.menu_ok);
        menuItemAnnulla = menu.findItem(R.id.menu_annulla);
        inizializzaAzioniMenu();
        return true;
    }

    private void inizializzaAzioniMenu() {
        menuItemOK.setOnMenuItemClickListener(Applicazione.getInstance().getControlloFormStudente().getAzioneModificaStudente());
        menuItemAnnulla.setOnMenuItemClickListener(Applicazione.getInstance().getControlloFormStudente().getAzioneAnnulla());
    }
}
