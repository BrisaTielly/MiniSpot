package com.example.model;

import java.time.Duration;

public abstract class Faixa {
    private long id;
    private String nome;
    private Duration duracao;

    public Faixa() {
    }

    public Faixa(long id, String nome, Duration duracao) {
        this.id = id;
        this.nome = nome;
        this.duracao = duracao;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Duration getDuracao() {
        return duracao;
    }

    public void setDuracao(Duration duracao) {
        this.duracao = duracao;
    }

    public abstract void tocar();
    public abstract void exibirInfo();
}

