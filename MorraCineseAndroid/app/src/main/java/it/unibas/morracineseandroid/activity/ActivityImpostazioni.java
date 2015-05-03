package it.unibas.morracineseandroid.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import it.unibas.morracineseandroid.R;


public class ActivityImpostazioni extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.impostazioni);
    }
}
