package it.unibas.morracineseandroid;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import it.unibas.morracineseandroid.activity.ActivityPrincipale;
import it.unibas.morracineseandroid.controllo.ControlloMenu;
import it.unibas.morracineseandroid.controllo.ControlloPrincipale;
import it.unibas.morracineseandroid.modello.Gioco;
import it.unibas.morracineseandroid.modello.Modello;

public class Applicazione extends Application {

    public static final String TAG = Applicazione.class.getSimpleName();

    private static Applicazione singleton;

    public static Applicazione getInstance() {
        return singleton;
    }

    public void onCreate() {
        Log.d(TAG, "Applicazione creata...");
        singleton = (Applicazione) getApplicationContext();
        singleton.registerActivityLifecycleCallbacks(new GestoreAttivita());
        Gioco gioco = new Gioco();
        modello.putBean(Costanti.GIOCO, gioco);
    }

    /////////////////////////////////////////////

    private Activity currentActivity;

    private Modello modello = new Modello();

    private ControlloPrincipale controlloPrincipale = new ControlloPrincipale();
    private ControlloMenu controlloMenu = new ControlloMenu();

    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public Modello getModello() {
        return modello;
    }

    public ControlloPrincipale getControlloPrincipale() {
        return controlloPrincipale;
    }

    public ControlloMenu getControlloMenu() {
        return controlloMenu;
    }

    public boolean previousSessionDestroyed(Bundle savedInstanceState) {
        return this.modello.getBean(Costanti.PARTITA) == null && savedInstanceState != null;
    }

    public void riavviaApplicazione() {
        Intent intent = new Intent(this, ActivityPrincipale.class);
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
