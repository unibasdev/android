package it.unibas.mediapesataandroid.vista;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import it.unibas.mediapesataandroid.R;

public class VistaImpostazioni extends PreferenceFragmentCompat {

    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.impostazioni, rootKey);
    }

}
