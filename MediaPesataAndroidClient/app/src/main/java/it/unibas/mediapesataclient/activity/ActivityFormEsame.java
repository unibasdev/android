package it.unibas.mediapesataclient.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import it.unibas.mediapesataclient.R;
import it.unibas.mediapesataclient.vista.VistaFormEsame;
import it.unibas.mediapesataclient.vista.VistaLogin;

public class ActivityFormEsame extends AppCompatActivity {

    public static final String TAG = ActivityFormEsame.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_esame);
    }

    public VistaFormEsame getVistaFormEsame() {
        return (VistaFormEsame) this.getSupportFragmentManager().findFragmentById(R.id.vistaFormEsame);
    }

    public void mostraMessaggioErrori(String messaggio) {
        Toast.makeText(this, messaggio, Toast.LENGTH_SHORT).show();
    }

}
