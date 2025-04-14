package it.unibas.convertitore.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import it.unibas.convertitore.Applicazione;
import it.unibas.convertitore.R;
import it.unibas.convertitore.vista.VistaPrincipale;

public class ActivityPrincipale extends AppCompatActivity {

    public static final String TAG = "ActivityPrincipale";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()::ActivityPrincipale. SavedInstance: " + savedInstanceState);
        setContentView(R.layout.activity_principale);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_principale, menu);
        MenuItem menuItemInformazioni = menu.findItem(R.id.menu_impostazioni);
        menuItemInformazioni.setOnMenuItemClickListener(Applicazione.getInstance().getControlloMenu().getAzioneImpostazioni());
        return true;
    }

    public VistaPrincipale getVistaPrincipale() {
        return (VistaPrincipale) this.getSupportFragmentManager().findFragmentById(R.id.vistaPrincipale);
    }

    public void mostraImpostazioni(){
        startActivity(new Intent(this, ActivityImpostazioni.class));
    }

}
