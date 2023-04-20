package it.unibas.wordle.vista;

import static it.unibas.wordle.modello.Costanti.TENTATIVO_PASSATO;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import it.unibas.wordle.Applicazione;
import it.unibas.wordle.R;
import it.unibas.wordle.modello.Costanti;
import it.unibas.wordle.modello.Partita;
import it.unibas.wordle.modello.Suggerimento;
import it.unibas.wordle.modello.Tentativo;

public class AdapterTentativi extends BaseAdapter {
    private static String TAG = AdapterTentativi.class.getName();

    private Partita partita;

    public void setPartita(Partita partita) {
        this.partita = partita;
    }

    @Override
    public int getCount() {
        return Costanti.MAX_TENTATIVI;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View riga;
        int tipoRiga = getItemViewType(i);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) Applicazione.getInstance().getSystemService(Applicazione.LAYOUT_INFLATER_SERVICE);
            if (tipoRiga == 0) {
                riga = layoutInflater.inflate(R.layout.riga_tentativo_passato, viewGroup, false);
            } else if (tipoRiga == 1) {
                riga = layoutInflater.inflate(R.layout.riga_tentativo_corrente, viewGroup, false);
            } else {
                riga = layoutInflater.inflate(R.layout.riga_tentativo_futuro, viewGroup, false);
            }
        } else {
            riga = view;
        }
        if (tipoRiga == Costanti.TENTATIVO_PASSATO) {
            Tentativo tentativo = partita.getTentativo(i);
            impostaTextView((TextView) riga.findViewById(R.id.s1), tentativo.getSuggerimenti().get(0));
            impostaTextView((TextView) riga.findViewById(R.id.s2), tentativo.getSuggerimenti().get(1));
            impostaTextView((TextView) riga.findViewById(R.id.s3), tentativo.getSuggerimenti().get(2));
            impostaTextView((TextView) riga.findViewById(R.id.s4), tentativo.getSuggerimenti().get(3));
            impostaTextView((TextView) riga.findViewById(R.id.s5), tentativo.getSuggerimenti().get(4));
        }
        if (tipoRiga == Costanti.TENTATIVO_CORRENTE) {
            EditText t1 = riga.findViewById(R.id.t1);
            EditText t2 = riga.findViewById(R.id.t2);
            EditText t3 = riga.findViewById(R.id.t3);
            EditText t4 = riga.findViewById(R.id.t4);
            EditText t5 = riga.findViewById(R.id.t5);
            impostaSuccessivo(t1, t2);
            impostaSuccessivo(t2, t3);
            impostaSuccessivo(t3, t4);
            impostaSuccessivo(t4, t5);
            impostaPrecedente(t2, t1);
            impostaPrecedente(t3, t2);
            impostaPrecedente(t4, t3);
            impostaPrecedente(t5, t4);
            t5.setOnEditorActionListener(Applicazione.getInstance().getControlloPrincipale().getAzioneSottomettiTentativo());
            t1.requestFocus();
        }
        return riga;
    }

    private void impostaSuccessivo(final EditText corrente, final EditText successiva) {
        corrente.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (corrente.getText().length() == 1) {
                    successiva.requestFocus();
                }
            }
        });
    }

    private void impostaPrecedente(final EditText corrente, final EditText precedente) {
        corrente.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    corrente.setText("");
                    precedente.requestFocus();
                }
                return true;
            }
        });
    }

    private void impostaTextView(TextView textView, Suggerimento suggerimento) {
        textView.setText(suggerimento.getLettera() + "");
        if (suggerimento.getSuggerimento().equals(Costanti.CORRETTA)) {
            textView.setBackgroundColor(Applicazione.getInstance().getResources().getColor(R.color.corretta));
        } else if (suggerimento.getSuggerimento().equals(Costanti.POSIZIONE_SCORRETTA)) {
            textView.setBackgroundColor(Applicazione.getInstance().getResources().getColor(R.color.posizione_scorretta));
        } else {
            textView.setBackgroundColor(Applicazione.getInstance().getResources().getColor(R.color.non_presente));
        }
    }

    public void aggiornaDati() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (partita != null && position < partita.getNumeroTentativi()) {
            return Costanti.TENTATIVO_PASSATO;
        } else if (partita != null && position == partita.getNumeroTentativi() && !partita.isConclusa()) {
            return Costanti.TENTATIVO_CORRENTE;
        } else {
            return Costanti.TENTATIVO_FUTURO;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }
}
