package com.ptojetodb.projetodb.model;


public enum NecessidadeIdoso {
    MENTAL("Mental"),
    PROFISSIONAL("Profissional"),
    SOCIAL("Social"),
    FISICA("Física"),
    TECNOLOGICA("Tecnológica");

    private final String categoria;

    NecessidadeIdoso(String categoria) {
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }
}



