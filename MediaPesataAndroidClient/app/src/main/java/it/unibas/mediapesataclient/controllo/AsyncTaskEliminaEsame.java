package it.unibas.mediapesataclient.controllo;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import it.unibas.mediapesataclient.Applicazione;
import it.unibas.mediapesataclient.activity.ActivityFormEsame;
import it.unibas.mediapesataclient.activity.ActivityStudente;
import it.unibas.mediapesataclient.modello.Costanti;
import it.unibas.mediapesataclient.modello.Esame;
import it.unibas.mediapesataclient.modello.Studente;
import it.unibas.mediapesataclient.modello.dto.RispostaErrore;
import it.unibas.mediapesataclient.vista.VistaFormEsame;
import it.unibas.mediapesataclient.vista.VistaStudente;

public class AsyncTaskEliminaEsame extends AsyncTask<Void, Void, String> {
    private String TAG = AsyncTaskEliminaEsame.class.getSimpleName();
    private Esame esame;

    public AsyncTaskEliminaEsame(Esame esame) {
        this.esame = esame;
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
        String indirizzo = Costanti.INDIRIZZO_REST + "/api/v1/esami/"+esame.getId();
        try {
            String token = (String) Applicazione.getInstance().getModello().getBean(Costanti.AUTH_TOKEN);
            Log.d(TAG, "Indirizzo: " + indirizzo);
            URL url = new URL(indirizzo);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("DELETE");
            urlConnection.setRequestProperty("Authorization", "Bearer " + token);
            urlConnection.connect();
            if (urlConnection.getResponseCode() != 204) {
                InputStream streamErrore = urlConnection.getErrorStream();
                RispostaErrore rispostaErrore = Applicazione.getInstance().getDaoGenericoJson().carica(streamErrore, RispostaErrore.class);
                Log.w(TAG, "Errore durante l'eliminazione" + rispostaErrore.getError());
                return rispostaErrore.getError();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String idInserito = reader.readLine();
            Log.d(TAG, "Risposta: " + idInserito);
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
        Studente studente = (Studente) Applicazione.getInstance().getModello().getBean(Costanti.STUDENTE);
        new AsyncTaskGetEsamiStudente(studente.getId()).execute();
        new AsyncTaskGetMediaPesata(studente.getId()).execute();
        if (errore != null) {
            activityStudente.mostraMessaggioErrori(errore);
            return;
        }
    }
}
