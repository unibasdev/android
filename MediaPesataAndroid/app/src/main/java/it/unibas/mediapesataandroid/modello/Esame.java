package it.unibas.mediapesataandroid.modello;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Esame {

    private String insegnamento;
    private int voto;
    private boolean lode;
    private int crediti;
    private Calendar dataRegistrazione;

    public Esame(String insegnamento, int voto, boolean lode, int crediti, Calendar dataRegistrazione) {
        setInsegnamento(insegnamento);
        setVoto(voto);
        setLode(lode);
        setCrediti(crediti);
        setDataRegistrazione(dataRegistrazione);
    }

    public String getInsegnamento() {
        return this.insegnamento;
    }

    public int getVoto() {
        return this.voto;
    }

    public int getCrediti() {
        return this.crediti;
    }

    public boolean isLode() {
        return this.lode;
    }

    public void setInsegnamento(String insegnamento) {
        this.insegnamento = insegnamento;
    }

    public void setVoto(int voto) {
        if (voto < 18 || voto > 30) {
            throw new IllegalArgumentException("Valore scorretto del voto");
        }
        this.voto = voto;
    }

    public void setCrediti(int crediti) {
        if (crediti <= 0) {
            throw new IllegalArgumentException("Valore scorretto dei crediti");
        }
        this.crediti = crediti;
    }

    public void setLode(boolean lode) {
        if (lode == true && this.voto != 30) {
            throw new IllegalArgumentException("La lode e' possibile solo con il 30");
        }
        this.lode = lode;
    }

    public Calendar getDataRegistrazione() {
        return dataRegistrazione;
    }

    public void setDataRegistrazione(Calendar dataRegistrazione) {
        this.dataRegistrazione = dataRegistrazione;
    }

    public String getTestoDataRegistrazione() {
        DateFormat df = SimpleDateFormat.getDateInstance();
        return df.format(dataRegistrazione.getTime());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Esame di ").append(this.insegnamento).append(" (").append(this.crediti).append(" CFU) - voto: ").append(this.voto);
        if (this.lode) {
            sb.append(" e lode");
        }
        sb.append(" registrato il ").append(getTestoDataRegistrazione());
        return sb.toString();
    }

    public String toSaveString() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return this.insegnamento + " , " + this.crediti + " , "
                + this.voto + " , " + this.lode + " , " + df.format(dataRegistrazione.getTime());
    }
}
