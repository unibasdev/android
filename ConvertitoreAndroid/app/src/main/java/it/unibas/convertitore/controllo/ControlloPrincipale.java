package it.unibas.convertitore.controllo;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import it.unibas.convertitore.Applicazione;
import it.unibas.convertitore.Costanti;
import it.unibas.convertitore.R;
import it.unibas.convertitore.activity.ActivityPrincipale;
import it.unibas.convertitore.modello.Convertitore;
import it.unibas.convertitore.vista.VistaPrincipale;

public class ControlloPrincipale {

    public static final String TAG = ControlloPrincipale.class.getName();

    private final View.OnClickListener azioneConverti = new AzioneConverti();
    private final View.OnClickListener azioneReset = new AzioneReset();

    public View.OnClickListener getAzioneConverti() {
        return azioneConverti;
    }

    public View.OnClickListener getAzioneReset() {
        return azioneReset;
    }

    private class AzioneConverti implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Convertitore convertitore = (Convertitore) Applicazione.getInstance().getModello().getBean(Costanti.CONVERTITORE);
            ActivityPrincipale activity = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
            VistaPrincipale vistaPrincipale = activity.getVistaPrincipale();
            String stringaVoto30mi = vistaPrincipale.getVoto30mi();
            String stringaVotoLaurea = vistaPrincipale.getVotoLaurea();
            if (stringaVoto30mi != null && !stringaVoto30mi.isEmpty()) {
                Double voto30mi = convalidaVoto(stringaVoto30mi, Costanti.MIN_VOTO_ESAME, Costanti.MAX_VOTO_ESAME, vistaPrincipale);
                if (voto30mi != null) {
                    double votoConvertito = convertitore.convertiInVotoLaurea(voto30mi, getMaxVotoLaurea());
                    vistaPrincipale.cambiaVoto110mi(votoConvertito, getMaxVotoLaurea());
                }
            } else if (stringaVotoLaurea != null && !stringaVotoLaurea.isEmpty()) {
                Double votoLaurea = convalidaVoto(stringaVotoLaurea, getMinVotoLaurea(), getMaxVotoLaurea(), vistaPrincipale);
                if (votoLaurea != null) {
                    double votoConvertito = convertitore.convertiIn30mi(votoLaurea, getMaxVotoLaurea());
                    vistaPrincipale.cambiaVoto30mi(votoConvertito, getMaxVotoLaurea());
                }
            } else {
                segnalaErrore(Costanti.MIN_VOTO_ESAME, vistaPrincipale, Applicazione.getInstance().getString(R.string.inserisciIlVoto));
            }
            Log.d(TAG, "Conversione effettuata...");
        }

        private int getMaxVotoLaurea() {
            Applicazione applicazione = Applicazione.getInstance();
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicazione);
            String stringMaxVotoLaurea = sharedPreferences.getString(applicazione.getString(R.string.prefVotoMassimoLaurea), applicazione.getString(R.string.votoLaureaDefault));
            return Integer.parseInt(stringMaxVotoLaurea);
        }

        private int getMinVotoLaurea() {
            return (int) (getMaxVotoLaurea() * 0.6);
        }

        private Double convalidaVoto(String stringaVoto, int limiteInferiore, int limiteSuperiore, VistaPrincipale vista) {
            try {
                double voto = Double.parseDouble(stringaVoto);
                if (voto < limiteInferiore || voto > limiteSuperiore) {
                    String errore = Applicazione.getInstance().getString(R.string.valoreVotoScorretto, limiteInferiore, limiteSuperiore);
                    segnalaErrore(limiteInferiore, vista, errore);
                    return null;
                }
                return voto;
            } catch (NumberFormatException ex) {
                String errore = Applicazione.getInstance().getString(R.string.votoScorretto);
                segnalaErrore(limiteInferiore, vista, errore);
                return null;
            }
        }

        private void segnalaErrore(int limiteInferiore, VistaPrincipale vista, String errore) {
            if (limiteInferiore == Costanti.MIN_VOTO_ESAME) {
                vista.setErroreVoto30mi(errore);
            } else {
                vista.setErroreVoto110mi(errore);
            }
        }
    }

    private class AzioneReset implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            ActivityPrincipale activity = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
            VistaPrincipale vistaPrincipale = activity.getVistaPrincipale();
            vistaPrincipale.pulisci();
        }
    }


}
