package it.unibas.mediapesataandroid.vista;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.GregorianCalendar;

import it.unibas.mediapesataandroid.Applicazione;
import it.unibas.mediapesataandroid.R;
import it.unibas.mediapesataandroid.modello.EBean;
import it.unibas.mediapesataandroid.modello.Esame;
import it.unibas.mediapesataandroid.modello.ModelloPersistente;

public class VistaFormEsame extends Fragment {

    public static final String TAG = VistaFormEsame.class.getName();

    private EditText testoInsegnamento;
    private EditText testoCrediti;
    private EditText testoVoto;
    private CheckBox checkBoxLode;
    private DatePicker pickerData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.vista_form_esame, container, false);
        testoInsegnamento = (EditText) vista.findViewById(R.id.testoInsegnamento);
        testoCrediti = (EditText) vista.findViewById(R.id.testoCrediti);
        testoVoto = (EditText) vista.findViewById(R.id.testoVoto);
        checkBoxLode = (CheckBox) vista.findViewById(R.id.checkBoxLode);
        pickerData = (DatePicker) vista.findViewById(R.id.pickerData);
        inizializzaCampi();
        return vista;
    }

    private void inizializzaCampi() {
        ModelloPersistente modello = Applicazione.getInstance().getModello();
        Esame esame = (Esame) modello.getPersistentBean(EBean.ESAME, Esame.class);
        if(esame == null){
            //La form serve per creare un nuovo esame
            return;
        }
        testoInsegnamento.setText(esame.getInsegnamento());
        testoCrediti.setText(esame.getCrediti() + "");
        testoVoto.setText(esame.getVoto() + "");
        checkBoxLode.setChecked(esame.isLode());
        Calendar data = esame.getDataRegistrazione();
        pickerData.updateDate(data.get(Calendar.YEAR), data.get(Calendar.MONTH), data.get(Calendar.DAY_OF_MONTH));
    }


    public String getInsegnamento() {
        return testoInsegnamento.getText().toString();
    }


    public void setErroreInsegnamento(String messaggio) {
        testoInsegnamento.setError(messaggio);
    }


    public String getCrediti() {
        return testoCrediti.getText().toString();
    }


    public void setErroreCrediti(String messaggio) {
        testoCrediti.setError(messaggio);
    }


    public String getVoto() {
        return testoVoto.getText().toString();
    }


    public void setErroreVoto(String messaggio) {
        testoVoto.setError(messaggio);
    }


    public boolean getLode() {
        return checkBoxLode.isChecked();
    }


    public void setErroreLode(String messaggio) {
        checkBoxLode.setError(messaggio);
    }


    public Calendar getDataRegistrazione() {
        return new GregorianCalendar(pickerData.getYear(), pickerData.getMonth(), pickerData.getDayOfMonth());
    }
}
