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
import it.unibas.mediapesataclient.activity.ActivityLogin;
import it.unibas.mediapesataclient.modello.Costanti;
import it.unibas.mediapesataclient.modello.Esame;
import it.unibas.mediapesataclient.modello.dto.RichiestaLogin;
import it.unibas.mediapesataclient.modello.dto.RispostaErrore;
import it.unibas.mediapesataclient.vista.VistaFormEsame;
import it.unibas.mediapesataclient.vista.VistaLogin;

public class AsyncTaskAggiungiEsame extends AsyncTask<Void, Void, String> {
    private String TAG = AsyncTaskAggiungiEsame.class.getSimpleName();
    private Esame esame;

    public AsyncTaskAggiungiEsame(Esame esame) {
        this.esame = esame;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ActivityFormEsame activityFormEsame = (ActivityFormEsame) Applicazione.getInstance().getCurrentActivity();
        VistaFormEsame vistaFormEsame = activityFormEsame.getVistaFormEsame();
        vistaFormEsame.mostraFinestraCaricamento();
    }

    @Override
    protected String doInBackground(Void... voids) {
        String indirizzo = Costanti.INDIRIZZO_REST + "/api/v1/esami";
        try {
            String token = (String) Applicazione.getInstance().getModello().getBean(Costanti.AUTH_TOKEN);
            Log.d(TAG, "Indirizzo: " + indirizzo);
            URL url = new URL(indirizzo);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Authorization", "Bearer " + token);
            urlConnection.setDoOutput(true);
            PrintWriter writer = new PrintWriter(urlConnection.getOutputStream());
            String bodyRichiesta = Applicazione.getInstance().getDaoGenericoJson().toJson(esame);
            Log.d(TAG, "Body richiesta: " + bodyRichiesta);
            writer.write(bodyRichiesta);
            writer.flush();
            urlConnection.connect();
            if (urlConnection.getResponseCode() != 200) {
                InputStream streamErrore = urlConnection.getErrorStream();
                RispostaErrore rispostaErrore = Applicazione.getInstance().getDaoGenericoJson().carica(streamErrore, RispostaErrore.class);
                Log.w(TAG, "Errore durante l'inserimento" + rispostaErrore.getError());
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
        ActivityFormEsame activityFormEsame = (ActivityFormEsame) Applicazione.getInstance().getCurrentActivity();
        VistaFormEsame vistaFormEsame = activityFormEsame.getVistaFormEsame();
        vistaFormEsame.chiudiFinestraCaricamento();
        if (errore != null) {
            activityFormEsame.mostraMessaggioErrori(errore);
            return;
        }
        activityFormEsame.finish();
    }
}
