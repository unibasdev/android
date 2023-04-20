package it.unibas.wordle.modello;

public class Suggerimento {

    private char lettera;
    private String suggerimento;

    public Suggerimento(char lettera, String suggerimento) {
        this.lettera = lettera;
        this.suggerimento = suggerimento;
    }

    public char getLettera() {
        return lettera;
    }

    public void setLettera(char lettera) {
        this.lettera = lettera;
    }

    public String getSuggerimento() {
        return suggerimento;
    }

    public void setSuggerimento(String suggerimento) {
        this.suggerimento = suggerimento;
    }
}
