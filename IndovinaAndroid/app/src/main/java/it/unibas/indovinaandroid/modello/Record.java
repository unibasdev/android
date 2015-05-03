package it.unibas.indovinaandroid.modello;

import android.util.Log;

public class Record {

    private boolean nuovoRecord;
    private boolean uguagliato;

    public boolean isNuovoRecord() {
        return this.nuovoRecord;
    }

    public boolean isUguagliato() {
        return this.uguagliato;
    }

    public void checkRecord(int tentativi, int valoreRecord) {
        assert (tentativi >= 1) : "Valore scorretto dei tentativi: " + tentativi;
        Log.d("Record", "Verifico nuovo record. Tentativo: " + tentativi + " - Record corrente: " + valoreRecord);
        this.nuovoRecord = false;
        this.uguagliato = false;
        if (valoreRecord < 1 || tentativi < valoreRecord) {
            this.nuovoRecord = true;
        } else if (tentativi == valoreRecord) {
            this.uguagliato = true;
        }
    }
}
