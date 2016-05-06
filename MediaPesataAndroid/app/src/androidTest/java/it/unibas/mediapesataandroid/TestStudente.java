package it.unibas.mediapesataandroid;

import android.util.Log;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.GregorianCalendar;

import it.unibas.mediapesataandroid.modello.Esame;
import it.unibas.mediapesataandroid.modello.Studente;

public class TestStudente extends TestCase{

    private static String TAG = "TestStudente";

    private Studente studente;
    private Esame esame1;
    private Esame esame2;
    private Esame esame3;

    public void setUp() {
        Log.d(TAG, "Sto eseguendo setUp()");
        studente = new Studente("Mario", "Rossi", 1234);
        esame1 = new Esame("Analisi", 24, false, 3, new GregorianCalendar());
        esame2 = new Esame("Geometria", 27, false, 9, new GregorianCalendar());
        esame3 = new Esame("Fisica", 30, true, 9, new GregorianCalendar());
    }

    public void testNoEsami() {
        try {
            studente.getMedia30mi();
            Assert.fail();
        } catch (Exception e) { }
    }

    public void testMediaPesata1() {
        Log.d(TAG, "Sto eseguendo testMediaPesata1()");
        studente.addEsame(esame1);
        studente.addEsame(esame2);
        assertEquals(26.25, studente.getMedia30mi());
    }

    public void testMediaPesata2() {
        Log.d(TAG, "Sto eseguendo testMediaPesata2()");
        studente.addEsame(esame1);
        assertEquals(24.0, studente.getMedia30mi());
    }

    public void testMediaPesata3() {
        Log.d(TAG, "Sto eseguendo testMediaPesata3()");
        studente.addEsame(esame1);
        studente.addEsame(esame3);
        assertEquals(28.5, studente.getMedia30mi());
    }

    public void testMediaPesata4() {
        Log.d(TAG, "Sto eseguendo testMediaPesata4()");
        studente.addEsame(esame1);
        studente.addEsame(esame2);
        studente.addEsame(esame3);
        assertEquals(27.85, studente.getMedia30mi(), 1E-2);
    }

}
