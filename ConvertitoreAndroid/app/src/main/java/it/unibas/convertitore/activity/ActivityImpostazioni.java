package it.unibas.convertitore.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import it.unibas.convertitore.R;

public class ActivityImpostazioni extends PreferenceActivity {

    @Override
    @SuppressWarnings("deprecation")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(getBaseContext(), R.xml.impostazioni, false);
        addPreferencesFromResource(R.xml.impostazioni);
    }
}
