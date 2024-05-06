package it.unibas.mediapesataclient;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import it.unibas.mediapesataclient.controllo.ControlloFormEsame;
import it.unibas.mediapesataclient.controllo.ControlloLogin;
import it.unibas.mediapesataclient.controllo.ControlloRicercaStudenti;
import it.unibas.mediapesataclient.controllo.ControlloStudente;
import it.unibas.mediapesataclient.modello.Modello;
import it.unibas.mediapesataclient.modello.ModelloPersistente;
import it.unibas.mediapesataclient.persistenza.DAOGenericoJson;

public class Applicazione extends Application {

    public static final String TAG = Applicazione.class.getSimpleName();

    private static Applicazione singleton;

    public static Applicazione getInstance() {
        return singleton;
    }

    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Applicazione creata...");
        singleton = (Applicazione) getApplicationContext();
        singleton.registerActivityLifecycleCallbacks(new GestoreAttivita());
    }

    /////////////////////////////////////////////

    private Activity currentActivity = null;

    private ControlloLogin controlloLogin = new ControlloLogin();
    private ControlloRicercaStudenti controlloRicercaStudenti = new ControlloRicercaStudenti();
    private ControlloStudente controlloStudente = new ControlloStudente();
    private ControlloFormEsame controlloFormEsame = new ControlloFormEsame();
    private DAOGenericoJson daoGenericoJson = new DAOGenericoJson();

    private Modello modello = new Modello();
    private ModelloPersistente modelloPersistente = new ModelloPersistente();

    public Activity getCurrentActivity() {
        return this.currentActivity;
    }

    public Modello getModello() {
        return modello;
    }

    public ModelloPersistente getModelloPersistente() {
        return modelloPersistente;
    }

    public ControlloLogin getControlloLogin() {
        return controlloLogin;
    }

    public ControlloRicercaStudenti getControlloRicercaStudenti() {
        return controlloRicercaStudenti;
    }

    public ControlloStudente getControlloStudente() {
        return controlloStudente;
    }

    public ControlloFormEsame getControlloFormEsame() {
        return controlloFormEsame;
    }

    public DAOGenericoJson getDaoGenericoJson() {
        return daoGenericoJson;
    }

    //////////////////////////////////////////////
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
