package it.unibas.mediapesataandroid;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import it.unibas.mediapesataandroid.controllo.ControlloFormEsame;
import it.unibas.mediapesataandroid.controllo.ControlloFormStudente;
import it.unibas.mediapesataandroid.controllo.ControlloMenu;
import it.unibas.mediapesataandroid.controllo.ControlloPrincipale;
import it.unibas.mediapesataandroid.modello.ModelloPersistente;
import it.unibas.mediapesataandroid.persistenza.DAOGenericoJson;

public class Applicazione extends Application {

    public static final String TAG = Applicazione.class.getSimpleName();

                private static Applicazione singleton;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Creata Applicazione");
        singleton = (Applicazione) getApplicationContext();
        singleton.registerActivityLifecycleCallbacks(new GestoreAttivita());
    }

    public static Applicazione getInstance() {
        return singleton;
    }
    /////////////////////////////////////////////

    private Activity currentActivity = null;

    private ModelloPersistente modello = new ModelloPersistente();

    private ControlloPrincipale controlloPrincipale = new ControlloPrincipale();
    private ControlloMenu controlloMenu = new ControlloMenu();
    private ControlloFormEsame controlloFormEsame = new ControlloFormEsame();
    private ControlloFormStudente controlloFormStudente = new ControlloFormStudente();
    private DAOGenericoJson daoStudente = new DAOGenericoJson();

    public ModelloPersistente getModello() {
        return this.modello;
    }

    public Activity getCurrentActivity() {
        return  currentActivity;
    }

    public ControlloMenu getControlloMenu() {
        return controlloMenu;
    }

    public ControlloPrincipale getControlloPrincipale() {
        return controlloPrincipale;
    }

    public ControlloFormEsame getControlloFormEsame() {
        return controlloFormEsame;
    }

    public ControlloFormStudente getControlloFormStudente() {
        return controlloFormStudente;
    }

    public DAOGenericoJson getDaoStudente() {
        return daoStudente;
    }

    //////////////////////////////////////////////
    //////////////////////////////////////////////
    private class GestoreAttivita implements ActivityLifecycleCallbacks {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            Log.i(TAG, "onActivityCreated: " + activity);
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            Log.i(TAG, "onActivityDestroyed: " + activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            Log.d(TAG, "onActivityStarted: " + activity);
        }

        @Override
        public void onActivityResumed(Activity activity) {
            Log.d(TAG, "currentActivity initialized: " + activity);
            currentActivity = activity;
        }

        @Override
        public void onActivityPaused(Activity activity) {
            Log.d(TAG, "onActivityPaused: " + activity);
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
            Log.d(TAG, "onActivitySaveInstanceState: " + activity);
        }
    }
}
