package it.unibas.mediapesataclient.modello;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Esame {
    private Long id;
    private Long studenteId;
    private String insegnamento;
    private int voto;
    private boolean lode;
    private int crediti;
    private LocalDate dataRegistrazione;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInsegnamento() {
        return insegnamento;
    }

    public void setInsegnamento(String insegnamento) {
        this.insegnamento = insegnamento;
    }

    public int getVoto() {
        return voto;
    }

    public void setVoto(int voto) {
        this.voto = voto;
    }

    public boolean isLode() {
        return lode;
    }

    public void setLode(boolean lode) {
        this.lode = lode;
    }

    public int getCrediti() {
        return crediti;
    }

    public void setCrediti(int crediti) {
        this.crediti = crediti;
    }

    public LocalDate getDataRegistrazione() {
        return dataRegistrazione;
    }

    public void setDataRegistrazione(LocalDate dataRegistrazione) {
        this.dataRegistrazione = dataRegistrazione;
    }

    public Long getStudenteId() {
        return studenteId;
    }

    public void setStudenteId(Long studenteId) {
        this.studenteId = studenteId;
    }

    public String getStringaDataRegistrazione() {
        return this.dataRegistrazione.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Esame{");
        sb.append("id=").append(id);
        sb.append(", studenteId=").append(studenteId);
        sb.append(", insegnamento='").append(insegnamento).append('\'');
        sb.append(", voto=").append(voto);
        sb.append(", lode=").append(lode);
        sb.append(", crediti=").append(crediti);
        sb.append(", dataRegistrazione=").append(dataRegistrazione);
        sb.append('}');
        return sb.toString();
    }
}
