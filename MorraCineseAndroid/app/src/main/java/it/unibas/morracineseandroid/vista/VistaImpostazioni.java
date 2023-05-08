package it.unibas.morracineseandroid.vista;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import it.unibas.morracineseandroid.R;

public class VistaImpostazioni extends PreferenceFragmentCompat {

    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.impostazioni, rootKey);
    }

}
