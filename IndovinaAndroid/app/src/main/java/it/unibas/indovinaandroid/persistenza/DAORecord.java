package it.unibas.indovinaandroid.persistenza;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import it.unibas.indovinaandroid.Applicazione;
import it.unibas.indovinaandroid.Costanti;

public class DAORecord {

    private static final String TAG = "DAORecord";

    public int carica() throws DAOException {
        SharedPreferences sharedPref = Applicazione.getInstance().getSharedPreferences(Costanti.FILE_RECORD, Context.MODE_PRIVATE);
        int record = sharedPref.getInt("record", 0);
        Log.d(TAG, "Caricato il valore del record: " + record);
        return record;
    }

    public void salva(int valoreRecord) throws DAOException {
        assert (1 <= valoreRecord) : "Valore scorretto del record: " + valoreRecord;
        Log.d(TAG, "Scrivo il valore del record " + valoreRecord);
        SharedPreferences sharedPref = Applicazione.getInstance().getSharedPreferences(Costanti.FILE_RECORD, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("record", valoreRecord);
        editor.commit();
    }
}
