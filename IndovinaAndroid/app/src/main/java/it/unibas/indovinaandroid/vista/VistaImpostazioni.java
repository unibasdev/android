package it.unibas.indovinaandroid.vista;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import it.unibas.indovinaandroid.R;

public class VistaImpostazioni extends PreferenceFragmentCompat {

    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.impostazioni, rootKey);
    }

}
