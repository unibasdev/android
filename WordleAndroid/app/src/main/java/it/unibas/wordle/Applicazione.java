package it.unibas.wordle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import it.unibas.wordle.controllo.ControlloPrincipale;
import it.unibas.wordle.modello.Costanti;
import it.unibas.wordle.modello.Modello;
import it.unibas.wordle.modello.ModelloPersistente;
import it.unibas.wordle.modello.Operatore;
import it.unibas.wordle.modello.Partita;
import it.unibas.wordle.modello.Suggerimento;
import it.unibas.wordle.modello.Tentativo;
import it.unibas.wordle.persistenza.DAOGenericoJson;

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

    private Modello modello = new Modello();
    private ModelloPersistente modelloPersistente = new ModelloPersistente();
    private DAOGenericoJson daoGenericoJson = new DAOGenericoJson();
    private Operatore operatore = new Operatore();

    private ControlloPrincipale controlloPrincipale = new ControlloPrincipale();

    public Activity getCurrentActivity() {
        return this.currentActivity;
    }

    public Modello getModello() {
        return modello;
    }

    public ModelloPersistente getModelloPersistente() {
        return modelloPersistente;
    }

    public ControlloPrincipale getControlloPrincipale() {
        return controlloPrincipale;
    }

    public Operatore getOperatore() {
        return operatore;
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
