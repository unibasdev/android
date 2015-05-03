package it.unibas.indovinaandroid.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import it.unibas.indovinaandroid.R;


public class ActivityImpostazioni extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.impostazioni);
    }
}
