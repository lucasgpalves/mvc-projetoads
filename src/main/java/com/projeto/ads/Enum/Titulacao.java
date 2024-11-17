package com.projeto.ads.Enum;

public enum Titulacao {
    POS("POS"),
    MESTRADO("MESTRADO"),
    DOUTORADO("DOUTORADO"),
    POSTDOC("POSDOC");

    private String titulo;
    private Titulacao(String titulo) {
        this.titulo = titulo;
    }
    public String getStatus() {
        return titulo;
    }
}
