package com.example.model;

import java.time.Duration;

public class Podcast extends Faixa {
    private String apresentador;

    public Podcast() {
    }

    public Podcast(long id, String nome, Duration duracao, String apresentador) {
        super(id, nome, duracao);
        this.apresentador = apresentador;
    }

    public String getApresentador() {
        return apresentador;
    }

    public void setApresentador(String apresentador) {
        this.apresentador = apresentador;
    }

    @Override
    public void tocar() {
        System.out.println("Tocando podcast: " + getNome() + (apresentador != null ? " por " + apresentador : ""));
    }

    @Override
    public void exibirInfo() {
        System.out.println("Podcast: " + getNome() +
                (apresentador != null ? " | Apresentador: " + apresentador : "") +
                " | Duração: " + getDuracao());
    }
}
