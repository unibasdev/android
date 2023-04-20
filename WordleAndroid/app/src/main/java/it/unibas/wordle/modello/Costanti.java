package it.unibas.wordle.modello;

public class Costanti {

    public final static String SERVER_URL = "http://informatica.unibas.it/rest/dizionario/";

    public static final int MAX_TENTATIVI = 5;

    public static final String ARCHIVIO = "ARCHIVIO";
    public static final String PARTITA = "PARTITA";

    public static final String CORRETTA = "CORRETTA";
    public static final String POSIZIONE_SCORRETTA = "POSIZIONE_SCORRETTA";
    public static final String NON_PRESENTE = "NON_PRESENTE";

    public static final int     TENTATIVO_PASSATO = 0;
    public static final int     TENTATIVO_CORRENTE = 1;
    public static final int     TENTATIVO_FUTURO = 2;
}
