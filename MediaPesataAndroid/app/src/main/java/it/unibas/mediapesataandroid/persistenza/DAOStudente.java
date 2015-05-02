package it.unibas.mediapesataandroid.persistenza;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import it.unibas.mediapesataandroid.modello.Esame;
import it.unibas.mediapesataandroid.modello.Studente;

public class DAOStudente {

    /* ******************************************
     *               Salvataggio
     * ****************************************** */
    public void salva(Studente studente, String nomeFile) throws DAOException {
        
        java.io.PrintWriter flusso = null;
        try {
            java.io.FileWriter fileWriter = new java.io.FileWriter(nomeFile);
            flusso = new java.io.PrintWriter(fileWriter);
            flusso.println("Studente:");
            flusso.println(studente.toSaveString());
            flusso.println("--------------------------------------");
            salvaEsami(studente, flusso);
        } catch (IOException ioe) {
            throw new DAOException(ioe);
        } finally {
            if (flusso != null) {
                flusso.close();
            }
        }
    }

    private void salvaEsami(Studente studente, java.io.PrintWriter flusso) {
        for (int i = 0; i < studente.getNumeroEsami(); i++) {
            flusso.println(studente.getEsame(i).toSaveString());
        }
    }

    /* ******************************************
     *               Caricamento
     * ****************************************** */
    public Studente carica(String nomeFile) throws DAOException {
        Studente studente = new Studente();
        java.io.BufferedReader flusso = null;
        try {
            java.io.FileReader fileReader = new java.io.FileReader(nomeFile);
            flusso = new java.io.BufferedReader(fileReader);
            estraiDatiStudente(studente, flusso);
            caricaEsami(studente, flusso);
        } catch (Exception e) {
            throw new DAOException(e);
        } finally {
            try {
                if (flusso != null) {
                    flusso.close();
                }
            } catch (java.io.IOException ioe) {
            }
        }
        return studente;
    }

    private void estraiDatiStudente(Studente studente, java.io.BufferedReader flusso)
            throws java.io.IOException {
        flusso.readLine();
        String lineaStudente = flusso.readLine();
        java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(lineaStudente, ",");
        studente.setCognome(tokenizer.nextToken().trim());
        studente.setNome(tokenizer.nextToken().trim());
        studente.setMatricola(Integer.parseInt(tokenizer.nextToken().trim()));
    }

    private void caricaEsami(Studente studente, java.io.BufferedReader flusso)
            throws java.io.IOException, ParseException {
        flusso.readLine();
        String lineaEsame;
        while ((lineaEsame = flusso.readLine()) != null) {
            studente.addEsame(estraiDatiEsame(lineaEsame, flusso));
        }
    }

    private Esame estraiDatiEsame(String lineaEsame, java.io.BufferedReader flusso) throws ParseException {
        java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(lineaEsame, ",");
        String insegnamento = tokenizer.nextToken().trim();
        int crediti = Integer.parseInt(tokenizer.nextToken().trim());
        int voto = Integer.parseInt(tokenizer.nextToken().trim());
        boolean lode = Boolean.valueOf(tokenizer.nextToken().trim());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar dataRegistrazione = new GregorianCalendar();
        Date data = df.parse(tokenizer.nextToken().trim());
        dataRegistrazione.setTime(data);
        return new Esame(insegnamento, voto, lode, crediti, dataRegistrazione);
    }

}
