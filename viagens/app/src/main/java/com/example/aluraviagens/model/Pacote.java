package com.example.aluraviagens.model;

import java.math.BigDecimal;

public class Pacote {
    private final String local;
    private final String imagem;
    private final int periodo;
    private final BigDecimal valor;

    public Pacote(String local, String imagem, int periodo, BigDecimal valor){
        this.local = local;
        this.imagem = imagem;
        this.periodo = periodo;
        this.valor = valor;
    }

    public String getLocal() {
        return local;
    }

    public String getImagem() {
        return imagem;
    }

    public int getPeriodo() {
        return periodo;
    }

    public BigDecimal getValor() {
        return valor;
    }
}
