package it.unibas.morracineseandroid.vista;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import it.unibas.morracineseandroid.Applicazione;
import it.unibas.morracineseandroid.Costanti;
import it.unibas.morracineseandroid.R;
import it.unibas.morracineseandroid.modello.Gioco;
import it.unibas.morracineseandroid.modello.Mano;
import it.unibas.morracineseandroid.modello.Modello;
import it.unibas.morracineseandroid.modello.Partita;

public class VistaPrincipale extends Fragment {

    public static final String TAG = VistaPrincipale.class.getName();

    private TextView labelPartiteVinteGiocatore;
    private TextView labelPartiteVinteComputer;
    private TextView labelManiVinteGiocatore;
    private TextView labelManiVinteComputer;
    private TextView labelPareggi;
    private TextView labelMessaggioPartita;
    private TextView labelMessaggioMano;
    private ImageView imageViewGiocataGiocatore;
    private ImageView imageViewGiocataComputer;
    private Button bottoneCarta;
    private Button bottoneForbici;
    private Button bottoneSasso;
    private int durataAnimazione;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.vista_principale, container, false);
        this.labelPartiteVinteGiocatore = (TextView) fragment.findViewById(R.id.labelPartiteVinteGiocatore);
        this.labelPartiteVinteComputer = (TextView) fragment.findViewById(R.id.labelPartiteVinteComputer);
        this.labelManiVinteGiocatore = (TextView) fragment.findViewById(R.id.labelManiVinteGiocatore);
        this.labelManiVinteComputer = (TextView) fragment.findViewById(R.id.labelManiVinteComputer);
        this.labelPareggi = (TextView) fragment.findViewById(R.id.labelPareggi);
        this.labelMessaggioPartita = (TextView) fragment.findViewById(R.id.labelMessaggioPartita);
        this.labelMessaggioMano = (TextView) fragment.findViewById(R.id.labelMessaggioMano);
        this.imageViewGiocataGiocatore = (ImageView) fragment.findViewById(R.id.imageViewGiocataGiocatore);
        this.imageViewGiocataComputer = (ImageView) fragment.findViewById(R.id.imageViewGiocataComputer);
        this.bottoneCarta = (Button) fragment.findViewById(R.id.bottoneCarta);
        this.bottoneCarta.setOnClickListener(Applicazione.getInstance().getControlloPrincipale().getAzioneGiocaCarta());
        this.bottoneForbici = (Button) fragment.findViewById(R.id.bottoneForbici);
        this.bottoneForbici.setOnClickListener(Applicazione.getInstance().getControlloPrincipale().getAzioneGiocaForbici());
        this.bottoneSasso = (Button) fragment.findViewById(R.id.bottoneSasso);
        this.bottoneSasso.setOnClickListener(Applicazione.getInstance().getControlloPrincipale().getAzioneGiocaSasso());
        durataAnimazione = getResources().getInteger(android.R.integer.config_mediumAnimTime);
        schermoIniziale();
        Log.i(TAG, "VistaPrincipale.onCreateView()");
        return fragment;
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState == null) {
            return;
        }
        if (savedInstanceState.getBoolean(Costanti.STATOBOTTONI)) {
            this.abilitaBottoniStatoPartita();
        } else {
            this.abilitaBottoniStatoNoPartita();
        }
        this.ripristinaIcone();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(Costanti.STATOBOTTONI, this.isStatoBottoniPartitaInCorso());
    }

    public void schermoIniziale() {
        abilitaBottoniStatoNoPartita();
        labelMessaggioPartita.setText(R.string.benvenuto);
        labelMessaggioMano.setText(R.string.usaIBottoniPerIniziare);
//        imageViewGiocataGiocatore.setImageDrawable(getResources().getDrawable(R.drawable.carta1));
//        imageViewGiocataComputer.setImageDrawable(getResources().getDrawable(R.drawable.forbici2));
        crossFadeImmagine(imageViewGiocataGiocatore, R.drawable.carta1);
        crossFadeImmagine(imageViewGiocataComputer, R.drawable.forbici2);
    }

    public void schermoInizialePartita() {
        abilitaBottoniStatoPartita();
        labelMessaggioPartita.setText(R.string.partitaInCorso);
        labelMessaggioMano.setText(R.string.scegliGiocata);
        Modello modello = Applicazione.getInstance().getModello();
        Partita partita = (Partita) modello.getBean(Costanti.PARTITA);
        aggiornaLabelRisultatoMano(partita);
    }

    public void schermoPartita() {
        Modello modello = Applicazione.getInstance().getModello();
        Partita partita = (Partita) modello.getBean(Costanti.PARTITA);
        Mano mano = (Mano) modello.getBean(Costanti.MANO);
        aggiornaLabelRisultatoMano(partita);
        cambiaIcone(mano);
        labelMessaggioPartita.setText(R.string.partitaInCorso);
        if (mano.getEsito() == Costanti.MANOVINTADALGIOCATORE) {
            labelMessaggioMano.setText(R.string.haiVintoLaMano);
        } else if (mano.getEsito() == Costanti.MANOVINTADALCOMPUTER) {
            labelMessaggioMano.setText(R.string.purtroppoHaiPersoLaMano);
        } else if (mano.getEsito() == Costanti.MANOINPAREGGIO) {
            labelMessaggioMano.setText(R.string.siEVerificatoUnPareggio);
        }
    }

    private void aggiornaLabelRisultatoMano(Partita partita) {
//        this.labelManiVinteGiocatore.setText(partita.getManiVinteDalGiocatore() + "");
//        this.labelManiVinteComputer.setText(partita.getManiVinteDalComputer() + "");
//        this.labelPareggi.setText(partita.getPareggi() + "");
        crossFadeTesto(this.labelManiVinteGiocatore, partita.getManiVinteDalGiocatore() + "");
        crossFadeTesto(this.labelManiVinteComputer, partita.getManiVinteDalComputer() + "");
        crossFadeTesto(this.labelPareggi, partita.getPareggi() + "");
    }

    private void cambiaIcone(Mano mano) {
//        this.imageViewGiocataGiocatore.setImageDrawable(getResources().getDrawable(getIconaGiocataGiocatore(mano.getGiocataGiocatore()), null));
//        this.imageViewGiocataComputer.setImageDrawable(getResources().getDrawable(getIconaGiocataComputer(mano.getGiocataComputer()), null));
        crossFadeImmagine(imageViewGiocataGiocatore, getIconaGiocataGiocatore(mano.getGiocataGiocatore()));
        crossFadeImmagine(imageViewGiocataComputer, getIconaGiocataComputer(mano.getGiocataComputer()));
    }

    public void schermoPartitaInterrotta() {
        abilitaBottoniStatoNoPartita();
        labelMessaggioPartita.setText(R.string.partitaInterrotta);
        labelMessaggioMano.setText(R.string.iniziaNuovaPartita);
    }

    public void schermoFinePartita() {
        bottoneCarta.setEnabled(false);
        bottoneForbici.setEnabled(false);
        bottoneSasso.setEnabled(false);
        Modello modello = Applicazione.getInstance().getModello();
        Partita partita = (Partita) modello.getBean(Costanti.PARTITA);
        labelMessaggioPartita.setText(R.string.partitaConclusa);
        if (partita.getStato() == Costanti.PARTITAVINTADALGIOCATORE) {
            labelMessaggioMano.setText(R.string.bravoHaiVintoLaPartita);
        } else if (partita.getStato() == Costanti.PARTITAVINTADALCOMPUTER) {
            labelMessaggioMano.setText(R.string.partitaVintaDalComputer);
        }
        Gioco gioco = (Gioco) modello.getBean(Costanti.GIOCO);
//        this.labelPartiteVinteGiocatore.setText(gioco.getPartiteVinteDalGiocatore() + "");
//        this.labelPartiteVinteComputer.setText(gioco.getPartiteVinteDalComputer() + "");
        crossFadeTesto(this.labelPartiteVinteGiocatore, gioco.getPartiteVinteDalGiocatore() + "");
        crossFadeTesto(this.labelPartiteVinteComputer, gioco.getPartiteVinteDalComputer() + "");
    }

    public void abilitaBottoniStatoNoPartita() {
        bottoneCarta.setEnabled(false);
        bottoneForbici.setEnabled(false);
        bottoneSasso.setEnabled(false);
    }

    public void abilitaBottoniStatoPartita() {
        bottoneCarta.setEnabled(true);
        bottoneForbici.setEnabled(true);
        bottoneSasso.setEnabled(true);
    }

    public int getIconaGiocataGiocatore(int giocata) {
        if (giocata == Costanti.CARTA) {
            return R.drawable.carta1;
        }
        if (giocata == Costanti.FORBICI) {
            return R.drawable.forbici1;
        }
        return R.drawable.sasso1;
    }

    public int getIconaGiocataComputer(int giocata) {
        if (giocata == Costanti.CARTA) {
            return R.drawable.carta2;
        }
        if (giocata == Costanti.FORBICI) {
            return R.drawable.forbici2;
        }
        return R.drawable.sasso2;
    }

    public boolean isStatoBottoniPartitaInCorso() {
        return bottoneCarta.isEnabled();
    }

    public void ripristinaIcone() {
        Modello modello = Applicazione.getInstance().getModello();
        Mano mano = (Mano) modello.getBean(Costanti.MANO);
        if (mano == null) {
            return;
        }
        cambiaIcone(mano);
    }

    private void crossFadeTesto(final TextView componente, final String nuovoValore) {
        if (componente.getText().toString().equals(nuovoValore)) {
            return;
        }
        componente.setAlpha(1f);
        componente.animate()
                .alpha(0f)
                .setDuration(durataAnimazione)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        componente.setText(nuovoValore);
                        componente.animate()
                                .alpha(1f)
                                .setDuration(durataAnimazione)
                                .setListener(null);
                    }
                });

    }

    private void crossFadeImmagine(final ImageView componente, final int idNuovaImmagine) {
        componente.setAlpha(1f);
        componente.animate()
                .alpha(0f)
                .setDuration(durataAnimazione)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        componente.setImageDrawable(getResources().getDrawable(idNuovaImmagine));
                        componente.animate()
                                .alpha(1f)
                                .setDuration(durataAnimazione)
                                .setListener(null);
                    }
                });

    }

}
