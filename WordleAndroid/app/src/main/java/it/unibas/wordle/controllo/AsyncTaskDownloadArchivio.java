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
import it.unibas.wordle.vista.VistaPrincipale;

public class AsyncTaskDownloadArchivio extends AsyncTask<Void, Void, Archivio> {

    private static final String TAG = AsyncTaskDownloadArchivio.class.getSimpleName();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
            ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
            VistaPrincipale vistaPrincipale = activityPrincipale.getVistaPrincipale();
            vistaPrincipale.mostraFinestraCaricamento();
    }

    @Override
    protected Archivio doInBackground(Void... voids) {
        try {
            String indirizzo = Costanti.SERVER_URL;
            Log.d(TAG, "Indirizzo: " + indirizzo);
            URL url = new URL(indirizzo);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream risultato = urlConnection.getInputStream();
            Archivio archivio = (Archivio) Applicazione.getInstance().getDaoGenericoJson().carica(risultato, Archivio.class);
            Log.d(TAG, "Archivio: " + archivio);
            return archivio;
        }catch(Exception e){
            Log.e(TAG, "Impossibile scaricare l'archivio " + e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(Archivio archivio) {
        super.onPostExecute(archivio);
        ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
        if(activityPrincipale == null){
            return;
        }
        VistaPrincipale vistaPrincipale = activityPrincipale.getVistaPrincipale();
        vistaPrincipale.chiudiFinestraCaricamento();
        if(archivio == null){
            activityPrincipale.mostraMessaggio("Errore durante il caricamento dei dati");
            return;
        }
        activityPrincipale.abilitaAzioneNuovaPartita();
        Applicazione.getInstance().getModello().putBean(Costanti.ARCHIVIO, archivio);
    }
}
