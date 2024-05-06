package it.unibas.mediapesataclient.controllo;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import it.unibas.mediapesataclient.Applicazione;
import it.unibas.mediapesataclient.activity.ActivityStudente;
import it.unibas.mediapesataclient.modello.Costanti;
import it.unibas.mediapesataclient.modello.Esame;
import it.unibas.mediapesataclient.modello.Studente;
import it.unibas.mediapesataclient.modello.dto.RispostaErrore;
import it.unibas.mediapesataclient.vista.VistaStudente;

public class AsyncTaskGetEsamiStudente extends AsyncTask<Void, Void, String> {
    private String TAG = AsyncTaskGetEsamiStudente.class.getSimpleName();
    private long idStudente;

    public AsyncTaskGetEsamiStudente(long idStudente) {
        this.idStudente = idStudente;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ActivityStudente activityStudente = (ActivityStudente) Applicazione.getInstance().getCurrentActivity();
        VistaStudente vistaStudente = activityStudente.getVistaStudente();
        vistaStudente.mostraFinestraCaricamento();
    }

    @Override
    protected String doInBackground(Void... voids) {
        String indirizzo = Costanti.INDIRIZZO_REST + "/api/v1/studenti/" + idStudente + "/esami";
        try {
            String token = (String) Applicazione.getInstance().getModello().getBean(Costanti.AUTH_TOKEN);
            Log.d(TAG, "Indirizzo: " + indirizzo);
            URL url = new URL(indirizzo);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Authorization", "Bearer " + token);
            urlConnection.connect();
            if (urlConnection.getResponseCode() != 200) {
                InputStream streamErrore = urlConnection.getErrorStream();
                RispostaErrore rispostaErrore = Applicazione.getInstance().getDaoGenericoJson().carica(streamErrore, RispostaErrore.class);
                Log.w(TAG, "Errore durante la richiesta" + rispostaErrore.getError());
                return rispostaErrore.getError();
            }
            List<Esame> esami = Applicazione.getInstance().getDaoGenericoJson().caricaLista(urlConnection.getInputStream(), Esame.class);
            Studente studente = (Studente) Applicazione.getInstance().getModello().getBean(Costanti.STUDENTE);
            studente.setListaEsami(esami);
            return null;
        } catch (Exception e) {
            Log.w(TAG, "Errore durante la richiesta", e);
            return e.getLocalizedMessage();
        }
    }

    @Override
    protected void onPostExecute(String errore) {
        super.onPostExecute(errore);
        ActivityStudente activityStudente = (ActivityStudente) Applicazione.getInstance().getCurrentActivity();
        VistaStudente vistaStudente = activityStudente.getVistaStudente();
        vistaStudente.chiudiFinestraCaricamento();
        if (errore != null) {
            activityStudente.mostraMessaggioErrori(errore);
            return;
        }
        vistaStudente.aggiornaDati();
    }
}
