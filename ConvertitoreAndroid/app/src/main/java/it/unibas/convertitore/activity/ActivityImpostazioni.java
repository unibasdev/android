package it.unibas.convertitore.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

import it.unibas.convertitore.R;

public class ActivityImpostazioni extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impostazioni);
    }
}
