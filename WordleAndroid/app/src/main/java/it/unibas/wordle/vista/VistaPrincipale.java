package it.unibas.wordle.vista;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import it.unibas.wordle.Applicazione;
import it.unibas.wordle.R;
import it.unibas.wordle.modello.Costanti;
import it.unibas.wordle.modello.Partita;

public class VistaPrincipale extends Fragment {

    private ListView listaTentativi;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.vista_principale, container, false);
        this.listaTentativi = vista.findViewById(R.id.listaTentativi);
        this.listaTentativi.setAdapter(new AdapterTentativi());
        return vista;
    }

    public void aggiornaDati() {
        AdapterTentativi adapterTentativi = (AdapterTentativi) this.listaTentativi.getAdapter();
        Partita partita = (Partita) Applicazione.getInstance().getModello().getBean(Costanti.PARTITA);
        adapterTentativi.setPartita(partita);
        adapterTentativi.aggiornaDati();
        ripulisci();
    }

    private void ripulisci() {
        if (getLettera1() != null) getLettera1().setText("");
        if (getLettera2() != null) getLettera2().setText("");
        if (getLettera3() != null) getLettera3().setText("");
        if (getLettera4() != null) getLettera4().setText("");
        if (getLettera5() != null) getLettera5().setText("");
        if (getLettera1() != null) getLettera1().requestFocus();
    }

    public EditText getLettera1() {
        return this.listaTentativi.findViewById(R.id.t1);
    }

    public EditText getLettera2() {
        return this.listaTentativi.findViewById(R.id.t2);
    }

    public EditText getLettera3() {
        return this.listaTentativi.findViewById(R.id.t3);
    }

    public EditText getLettera4() {
        return this.listaTentativi.findViewById(R.id.t4);
    }

    public EditText getLettera5() {
        return this.listaTentativi.findViewById(R.id.t5);
    }

    public void setErroreLettera1(String errore) {
        EditText editText = this.listaTentativi.findViewById(R.id.t1);
        editText.setError(errore);
    }

    public void setErroreLettera2(String errore) {
        EditText editText = this.listaTentativi.findViewById(R.id.t2);
        editText.setError(errore);
    }

    public void setErroreLettera3(String errore) {
        EditText editText = this.listaTentativi.findViewById(R.id.t3);
        editText.setError(errore);
    }

    public void setErroreLettera4(String errore) {
        EditText editText = this.listaTentativi.findViewById(R.id.t4);
        editText.setError(errore);
    }

    public void setErroreLettera5(String errore) {
        EditText editText = this.listaTentativi.findViewById(R.id.t5);
        editText.setError(errore);
    }

    @Override
    public void onResume() {
        super.onResume();
        aggiornaDati();
    }

    public void mostraFinestraCaricamento() {
        progressDialog = ProgressDialog.show(Applicazione.getInstance().getCurrentActivity(), "Caricamento", "Caricamento in corso. Attendi...", true);
        progressDialog.show();
    }

    public void chiudiFinestraCaricamento() {
        progressDialog.dismiss();
    }
}
