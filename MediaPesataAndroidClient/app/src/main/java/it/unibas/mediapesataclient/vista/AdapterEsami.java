package it.unibas.mediapesataclient.vista;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import it.unibas.mediapesataclient.Applicazione;
import it.unibas.mediapesataclient.R;
import it.unibas.mediapesataclient.modello.Esame;
import it.unibas.mediapesataclient.modello.Studente;

public class AdapterEsami extends BaseAdapter {
    private List<Esame> esami;

    public AdapterEsami(List<Esame> esami) {
        this.esami = esami;
    }

    public void setEsami(List<Esame> esami) {
        this.esami = esami;
    }

    @Override
    public int getCount() {
        if (esami == null) return 0;
        return esami.size();
    }

    @Override
    public Object getItem(int position) {
        return esami.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View riga;
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) Applicazione.getInstance().getSystemService(Applicazione.LAYOUT_INFLATER_SERVICE);
            riga = layoutInflater.inflate(R.layout.riga_esame, parent, false);
        }else{
            riga = convertView;
        }
        Esame esame = esami.get(position);
        TextView labelInsegnamento = riga.findViewById(R.id.labelInsegnamento);
        TextView labelVoto = riga.findViewById(R.id.labelVoto);
        TextView labelCreditiData = riga.findViewById(R.id.labelCreditiData);
        labelInsegnamento.setText(esame.getInsegnamento());
        labelCreditiData.setText(esame.getCrediti() + " CFU - " + esame.getStringaDataRegistrazione());
        if (esame.isLode()) {
            labelVoto.setText(esame.getVoto() + "+");
        } else {
            labelVoto.setText(esame.getVoto() + "");
        }
        return riga;
    }
}
