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
import it.unibas.mediapesataclient.activity.ActivityLogin;
import it.unibas.mediapesataclient.modello.Costanti;
import it.unibas.mediapesataclient.modello.dto.RichiestaLogin;
import it.unibas.mediapesataclient.modello.dto.RispostaErrore;
import it.unibas.mediapesataclient.vista.VistaLogin;

public class AsyncTaskLogin extends AsyncTask<Void, Void, String> {
    private String TAG = AsyncTaskLogin.class.getSimpleName();
    private RichiestaLogin richiestaLogin;

    public AsyncTaskLogin(RichiestaLogin richiestaLogin) {
        this.richiestaLogin = richiestaLogin;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ActivityLogin activityLogin = (ActivityLogin) Applicazione.getInstance().getCurrentActivity();
        VistaLogin vistaLogin = activityLogin.getVistaLogin();
        vistaLogin.mostraFinestraCaricamento();
    }

    @Override
    protected String doInBackground(Void... voids) {
        String indirizzo = Costanti.INDIRIZZO_REST + "/api/v1/utenti/login";
        try {
            Log.d(TAG, "Indirizzo: " + indirizzo);
            URL url = new URL(indirizzo);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setDoOutput(true);
            PrintWriter writer = new PrintWriter(urlConnection.getOutputStream());
            String bodyRichiesta = Applicazione.getInstance().getDaoGenericoJson().toJson(richiestaLogin);
            Log.d(TAG, "Body richiesta: " + bodyRichiesta);
            writer.write(bodyRichiesta);
            writer.flush();
            urlConnection.connect();
            if (urlConnection.getResponseCode() != 200) {
                InputStream streamErrore = urlConnection.getErrorStream();
                RispostaErrore rispostaErrore = Applicazione.getInstance().getDaoGenericoJson().carica(streamErrore, RispostaErrore.class);
                Log.w(TAG, "Errore durante il login" + rispostaErrore.getError());
                return rispostaErrore.getError();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String token = reader.readLine();
            Log.d(TAG, "Risposta: " + token);
            Applicazione.getInstance().getModello().putBean(Costanti.AUTH_TOKEN, token);
            return null;
        } catch (Exception e) {
            Log.w(TAG, "Errore durante la richiesta", e);
            return e.getLocalizedMessage();
        }
    }

    @Override
    protected void onPostExecute(String errore) {
        super.onPostExecute(errore);
        ActivityLogin activityLogin = (ActivityLogin) Applicazione.getInstance().getCurrentActivity();
        VistaLogin vistaLogin = activityLogin.getVistaLogin();
        vistaLogin.chiudiFinestraCaricamento();
        if (errore != null) {
            activityLogin.mostraMessaggioErrori(errore);
            return;
        }
        activityLogin.mostraActivityRicercaStudenti();
    }
}
