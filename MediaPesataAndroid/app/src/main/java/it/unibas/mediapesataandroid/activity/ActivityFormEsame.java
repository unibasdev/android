package it.unibas.mediapesataandroid.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import it.unibas.mediapesataandroid.Applicazione;
import it.unibas.mediapesataandroid.Costanti;
import it.unibas.mediapesataandroid.R;
import it.unibas.mediapesataandroid.modello.Esame;
import it.unibas.mediapesataandroid.modello.ModelloPersistente;
import it.unibas.mediapesataandroid.vista.VistaFormEsame;

public class ActivityFormEsame extends AppCompatActivity {

    public static final String TAG = "ActivityFormEsame";
    private MenuItem menuItemOK;
    private MenuItem menuItemAnnulla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_form_esame);
    }

    public VistaFormEsame getVistaFormEsame() {
        return (VistaFormEsame) this.getSupportFragmentManager().findFragmentById(R.id.vistaFormEsame);
    }
    ////////////////////////// MENU ////////////////////////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_form_esame, menu);
        menuItemOK = menu.findItem(R.id.menu_ok);
        menuItemAnnulla = menu.findItem(R.id.menu_annulla);
        menuItemAnnulla.setOnMenuItemClickListener(Applicazione.getInstance().getControlloFormEsame().getAzioneAnnulla());
        ModelloPersistente modello = Applicazione.getInstance().getModello();
        Esame esame = (Esame) modello.getPersistentBean(Costanti.ESAME, Esame.class);
        if (esame == null) {
            //La form serve per creare un nuovo esame
            menuItemOK.setOnMenuItemClickListener(Applicazione.getInstance().getControlloFormEsame().getAzioneAggiungiEsame());
        } else {
            //La form serve per modificare un esame esistente
            menuItemOK.setOnMenuItemClickListener(Applicazione.getInstance().getControlloFormEsame().getAzioneModificaEsame());
        }
        return true;
    }
}
