package it.unibas.mediapesataandroid.modello;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import it.unibas.mediapesataandroid.Applicazione;
import it.unibas.mediapesataandroid.Costanti;
import it.unibas.mediapesataandroid.R;

public class ModelloListaEsami extends BaseAdapter {

    public final static String TAG = ModelloListaEsami.class.getName();

    private Studente getStudente() {
        ModelloPersistente modello = Applicazione.getInstance().getModello();
        Studente studente = (Studente) modello.getPersistentBean(Costanti.STUDENTE, Studente.class);
        return studente;
    }

    @Override
    public int getCount() {
        if (getStudente() == null) {
            return 0;
        }
        return this.getStudente().getNumeroEsami();
    }

    @Override
    public Object getItem(int posizione) {
        return this.getStudente().getEsame(posizione);
    }

    @Override
    public long getItemId(int posizione) {
        return posizione;
    }

    @Override
    public View getView(int posizione, View viewRiciclabile, ViewGroup parent) {
        View riga;
        if (viewRiciclabile == null) {
            //Non c'è nulla da riciclare e creo una nuova View a partire dal layout xml
            LayoutInflater inflater = (LayoutInflater) Applicazione.getInstance().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            riga = inflater.inflate(R.layout.riga_esami, parent, false);
        } else {
            //Riutilizzo la view
            riga = viewRiciclabile;
        }
        Esame esame = this.getStudente().getEsame(posizione);
        Log.d(TAG, "Mostro esame " + esame.toString());
        TextView labelInsegnamento = (TextView) riga.findViewById(R.id.rigaTestoInsegnamento);
        labelInsegnamento.setText(esame.getInsegnamento());
        TextView labelData = (TextView) riga.findViewById(R.id.rigaTestoCreditiData);
        labelData.setText(esame.getCrediti() + " CFU - " + esame.getTestoDataRegistrazione());
        TextView labelVoto = (TextView) riga.findViewById(R.id.rigaTestoVoto);
        if (esame.isLode()) {
            labelVoto.setText(esame.getVoto() + "+");
        } else {
            labelVoto.setText(esame.getVoto() + "");
        }
        return riga;
    }

    public void updateContent() {
        this.notifyDataSetChanged();
    }
}
