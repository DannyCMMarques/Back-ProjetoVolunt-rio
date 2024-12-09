package com.ptojetodb.projetodb.model;


public enum HabilidadeVoluntario {
    PROFISSIONAL("Profissional"),
    NAO_PROFISSIONAL("Não Profissional"),
    AMBOS("Ambos");

    private final String categoria;

    HabilidadeVoluntario(String categoria) {
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }
}

