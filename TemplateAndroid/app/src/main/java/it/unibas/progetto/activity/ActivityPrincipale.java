package it.unibas.progetto.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import it.unibas.progetto.R;

public class ActivityPrincipale extends AppCompatActivity {

    public static final String TAG = ActivityPrincipale.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principale);
    }

}
