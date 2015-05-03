package it.unibas.indovinaandroid;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import it.unibas.indovinaandroid.activity.ActivityIniziale;
import it.unibas.indovinaandroid.controllo.ControlloIniziale;
import it.unibas.indovinaandroid.controllo.ControlloMenu;
import it.unibas.indovinaandroid.controllo.ControlloPartita;
import it.unibas.indovinaandroid.modello.Modello;
import it.unibas.indovinaandroid.persistenza.DAORecord;

public class Applicazione extends Application {

    public static final String TAG = Applicazione.class.getSimpleName();

    private static Applicazione singleton;

    @Override
    public void onCreate() {
        Log.d(TAG, "*** Creata Applicazione");
        singleton = (Applicazione) getApplicationContext();
        singleton.registerActivityLifecycleCallbacks(new GestoreAttivita());
    }

    public static Applicazione getInstance() {
        return singleton;
    }
    /////////////////////////////////////////////

    private Activity currentActivity = null;

    private Modello modello = new Modello();
    private DAORecord daoRecord = new DAORecord();

    private ControlloIniziale controlloIniziale = new ControlloIniziale();
    private ControlloPartita controlloPartita = new ControlloPartita();
    private ControlloMenu controlloMenu = new ControlloMenu();

    public Modello getModello() {
        return this.modello;
    }

    public DAORecord getDaoRecord() {
        return daoRecord;
    }

    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public ControlloIniziale getControlloIniziale() {
        return controlloIniziale;
    }

    public ControlloPartita getControlloPartita() {
        return controlloPartita;
    }

    public ControlloMenu getControlloMenu() {
        return controlloMenu;
    }

    public boolean previousSessionDestroyed(Bundle savedInstanceState) {
        return this.modello.getBean(Costanti.PARTITA) == null && savedInstanceState != null;
    }

    public void riavviaApplicazione() {
        Intent intent = new Intent(this, ActivityIniziale.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    //////////////////////////////////////////////
    private class GestoreAttivita implements ActivityLifecycleCallbacks {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            //Log.i(TAG, "onActivityCreated: " + activity);
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            //Log.i(TAG, "onActivityDestroyed: " + activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            //Log.d(TAG, "onActivityStarted: " + activity);
        }

        @Override
        public void onActivityResumed(Activity activity) {
            Log.d(TAG, "currentActivity initialized: " + activity);
            currentActivity = activity;
        }

        @Override
        public void onActivityPaused(Activity activity) {
            //Log.d(TAG, "onActivityPaused: " + activity);
        }

        @Override
        public void onActivityStopped(Activity activity) {
            if (currentActivity == activity) {
                Log.d(TAG, "currentActivity stopped: " + activity);
                currentActivity = null;
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            //Log.d(TAG, "onActivitySaveInstanceState: " + activity);
        }
    }
}
