package it.unibas.mediapesataclient.controllo;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import it.unibas.mediapesataclient.Applicazione;
import it.unibas.mediapesataclient.activity.ActivityRicercaStudenti;
import it.unibas.mediapesataclient.modello.Costanti;
import it.unibas.mediapesataclient.modello.Studente;
import it.unibas.mediapesataclient.modello.dto.RispostaErrore;
import it.unibas.mediapesataclient.vista.VistaRicercaStudenti;

public class AsyncTaskRicercaStudenti extends AsyncTask<Void, Void, String> {
    private String TAG = AsyncTaskRicercaStudenti.class.getSimpleName();
    private String nome;
    private String cognome;
    private String stringaAnno;

    public AsyncTaskRicercaStudenti(String nome, String cognome, String stringaAnno) {
        this.nome = nome;
        this.cognome = cognome;
        this.stringaAnno = stringaAnno;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ActivityRicercaStudenti activityRicercaStudenti = (ActivityRicercaStudenti) Applicazione.getInstance().getCurrentActivity();
        VistaRicercaStudenti vistaRicercaStudenti = activityRicercaStudenti.getVistaRicercaStudenti();
        vistaRicercaStudenti.mostraFinestraCaricamento();
    }

    @Override
    protected String doInBackground(Void... voids) {
        String indirizzo = Costanti.INDIRIZZO_REST + "/api/v1/studenti?";
        if (!nome.trim().isEmpty()) {
            indirizzo += "nome=" + nome + "&";
        }
        if (!cognome.trim().isEmpty()) {
            indirizzo += "cognome=" + cognome + "&";
        }
        if (!stringaAnno.trim().isEmpty()) {
            indirizzo += "annoIscrizione=" + stringaAnno;
        }
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
            List<Studente> studenti = Applicazione.getInstance().getDaoGenericoJson().caricaLista(urlConnection.getInputStream(), Studente.class);
            Log.d(TAG, "Studenti: " + studenti.size());
            Applicazione.getInstance().getModello().putBean(Costanti.STUDENTI, studenti);
            return null;
        } catch (Exception e) {
            Log.w(TAG, "Errore durante la richiesta", e);
            return e.getLocalizedMessage();
        }
    }

    @Override
    protected void onPostExecute(String errore) {
        super.onPostExecute(errore);
        ActivityRicercaStudenti activityRicercaStudenti = (ActivityRicercaStudenti) Applicazione.getInstance().getCurrentActivity();
        VistaRicercaStudenti vistaRicercaStudenti = activityRicercaStudenti.getVistaRicercaStudenti();
        vistaRicercaStudenti.chiudiFinestraCaricamento();
        if (errore != null) {
            activityRicercaStudenti.mostraMessaggioErrori(errore);
            return;
        }
        vistaRicercaStudenti.aggiornaDati();
    }
}
