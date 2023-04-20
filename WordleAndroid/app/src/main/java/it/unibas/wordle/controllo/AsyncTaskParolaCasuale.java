package it.unibas.wordle.controllo;

import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import it.unibas.wordle.Applicazione;
import it.unibas.wordle.activity.ActivityPrincipale;
import it.unibas.wordle.modello.Archivio;
import it.unibas.wordle.modello.Costanti;
import it.unibas.wordle.modello.Partita;
import it.unibas.wordle.vista.VistaPrincipale;

public class AsyncTaskParolaCasuale extends AsyncTask<Void, Void, String> {

    private static final String TAG = AsyncTaskParolaCasuale.class.getSimpleName();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
            ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
            VistaPrincipale vistaPrincipale = activityPrincipale.getVistaPrincipale();
            vistaPrincipale.mostraFinestraCaricamento();
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            String indirizzo = Costanti.SERVER_URL + "random/";
            Log.d(TAG, "Indirizzo: " + indirizzo);
            URL url = new URL(indirizzo);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream risultato = urlConnection.getInputStream();
            String parolaCasuale = (String) Applicazione.getInstance().getDaoGenericoJson().carica(risultato, String.class);
            Log.d(TAG, "parolaCasuale: " + parolaCasuale);
            return parolaCasuale;
        }catch(Exception e){
            Log.e(TAG, "Impossibile scaricare l'archivio " + e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(String parolaCasuale) {
        super.onPostExecute(parolaCasuale);
        ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
        VistaPrincipale vistaPrincipale = activityPrincipale.getVistaPrincipale();
        vistaPrincipale.chiudiFinestraCaricamento();
        if(parolaCasuale == null){
            activityPrincipale.mostraMessaggio("Errore durante il caricamento dei dati");
            return;
        }
        Partita partita = new Partita();
        partita.setParola(parolaCasuale);
        Applicazione.getInstance().getModello().putBean(Costanti.PARTITA, partita);
        Log.d(TAG, "Parola da indovinare: " + partita.getParola());
        activityPrincipale.disabilitaAzioneNuovaPartita();
        activityPrincipale.abilitaAzioneInterrompiPartita();
        activityPrincipale.getVistaPrincipale().aggiornaDati();
    }
}
