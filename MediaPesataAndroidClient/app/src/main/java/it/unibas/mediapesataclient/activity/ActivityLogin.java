package it.unibas.mediapesataclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import it.unibas.mediapesataclient.R;
import it.unibas.mediapesataclient.vista.VistaLogin;

public class ActivityLogin extends AppCompatActivity {

    public static final String TAG = ActivityLogin.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public VistaLogin getVistaLogin(){
        return (VistaLogin) this.getSupportFragmentManager().findFragmentById(R.id.vistaLogin);
    }

    public void mostraMessaggioErrori(String messaggio) {
        Toast.makeText(this, messaggio, Toast.LENGTH_SHORT).show();
    }

    public void mostraActivityRicercaStudenti() {
        this.startActivity(new Intent(this, ActivityRicercaStudenti.class));
    }
}
