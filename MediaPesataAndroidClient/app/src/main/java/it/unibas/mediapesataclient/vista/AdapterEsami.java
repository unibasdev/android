package it.unibas.mediapesataclient.vista;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import it.unibas.mediapesataclient.Applicazione;
import it.unibas.mediapesataclient.R;
import it.unibas.mediapesataclient.modello.Studente;

public class AdapterStudenti extends BaseAdapter {
    private List<Studente> studenti;

    public AdapterStudenti(List<Studente> studenti) {
        this.studenti = studenti;
    }

    public void setStudenti(List<Studente> studenti) {
        this.studenti = studenti;
    }

    @Override
    public int getCount() {
        if (studenti == null) return 0;
        return studenti.size();
    }

    @Override
    public Object getItem(int position) {
        return studenti.get(position);
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
            riga = layoutInflater.inflate(R.layout.riga_studente, parent, false);
        }else{
            riga = convertView;
        }
        Studente studente = studenti.get(position);
        TextView labelMatricola = riga.findViewById(R.id.labelMatricola);
        labelMatricola.setText(studente.getMatricola() + "");
        TextView labelNomeCognome = riga.findViewById(R.id.labelNomeCognome);
        labelNomeCognome.setText(studente.getNome() + " " + studente.getCognome());
        TextView labelAnno = riga.findViewById(R.id.labelAnno);
        labelAnno.setText(studente.getAnnoIscrizione() + "");
        return riga;
    }
}
