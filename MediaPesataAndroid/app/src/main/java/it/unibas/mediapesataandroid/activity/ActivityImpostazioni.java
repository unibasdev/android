package it.unibas.mediapesataandroid.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import it.unibas.mediapesataandroid.R;


public class ActivityImpostazioni extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.impostazioni);
    }
}
